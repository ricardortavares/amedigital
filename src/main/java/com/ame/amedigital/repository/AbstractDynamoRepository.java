package com.ame.amedigital.repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Expected;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.ame.amedigital.api.commons.JsonUtils;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public abstract class AbstractDynamoRepository<E, K> {

    @Autowired
    protected DynamoDB dynamoDB;

    @Autowired
    private JsonUtils jsonUtils;

    private final Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public AbstractDynamoRepository() {
        Class<E> entityClass = null;
        Class<?> clazz = this.getClass();
        while (!clazz.equals(java.lang.Object.class)) {
            Type type = clazz.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                entityClass = (Class<E>) ((ParameterizedType) type).getActualTypeArguments()[0];
                break;
            }
            clazz = clazz.getSuperclass();
        }
        this.entityClass = entityClass;
    }

    public final Optional<E> findById(K id) {
        return findOptionalById(id);
    }

    public Optional<E> findOptionalById(K id) {
        final Table table = getTable();

        Item item = table.getItem(getHashColumnName(), id);
        return Optional.ofNullable(item).map(this::fromItem);
    }

    /**
     * This method should be used when a previous check has confirmed that the id is
     * valid and we are sure that a NoDataFoundException will not be thrown. If
     * called incorrectly and the id does not exist, and IllegalStateException will
     * be thrown.
     *
     * @param id
     * @return
     */
    public E findByIdUnchecked(K id) {
        return findOptionalById(id).orElseThrow(() -> new IllegalStateException("not found: " + id));
    }

    public Optional<E> findByIdConsistent(K id) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(getHashColumnName(), id)
                .withConsistentRead(true);
        Item item = getTable().getItem(spec);
        return getItem(item);
    }


    public void delete(K id) {
        Objects.requireNonNull(id);
        final Table table = getTable();
        table.deleteItem(getHashColumnName(), id);
    }

    public void add(E entity) {
        Expected expected = new Expected(getHashColumnName()).notExist();
        try {
            save(entity, expected);
        } catch (ConditionalCheckFailedException e) {
            throw new RuntimeException("Data already exists", e);
        }
    }

    public void save(E entity, Expected expected) {
        Objects.requireNonNull(entity);
        final Table table = getTable();
        String objectJson = jsonUtils.toJson(entity);
        final Item newItem = Item.fromJSON(objectJson);
        if (expected == null) {
            table.putItem(newItem);
        } else {
            table.putItem(newItem, expected);
        }
    }

    public void save(E entity) {
        save(entity, null);
    }

    protected final E fromItem(Item item) {
        String itemJSon = item.toJSON();
        return jsonUtils.fromJson(itemJSon, entityClass);
    }

    protected final Table getTable() {
        return dynamoDB.getTable(getTableName());
    }

    public void scan(Consumer<E> consumer) {
        final Table table = getTable();
        ItemCollection<ScanOutcome> items = table.scan();
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item next = iterator.next();
            consumer.accept(fromItem(next));
        }
    }

    protected Optional<E> getFirstQuery(ItemCollection<QueryOutcome> items) {
        Iterator<Item> iter = items.iterator();
        if (iter.hasNext()) {
            Item item = iter.next();
            return Optional.of(fromItem(item));
        } else {
            return Optional.empty();
        }
    }

    protected Optional<E> getFirstScan(ItemCollection<ScanOutcome> items) {
        Iterator<Item> iter = items.iterator();
        if (iter.hasNext()) {
            Item item = iter.next();
            return Optional.of(fromItem(item));
        } else {
            return Optional.empty();
        }
    }

    protected Optional<E> getItem(Item item) {
        if (item != null) {
            return Optional.of(fromItem(item));
        } else {
            return Optional.empty();
        }
    }

    protected java.util.List<E> getAllQuery(ItemCollection<QueryOutcome> items) {
        Iterable<E> iterable = Iterables.transform(items, this::fromItem);
        return Lists.newArrayList(iterable);
    }

    protected java.util.List<E> getAllScan(ItemCollection<ScanOutcome> items) {
        Iterable<E> iterable = Iterables.transform(items, this::fromItem);
        return Lists.newArrayList(iterable);
    }

    protected abstract String getTableName();

    protected abstract String getHashColumnName();

}
