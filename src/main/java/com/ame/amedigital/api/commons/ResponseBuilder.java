package com.ame.amedigital.api.commons;

import com.ame.amedigital.api.commons.ResponseObject.ResponseError;
import com.google.common.base.Preconditions;

public class ResponseBuilder<T> {

    private T data;
    private ResponseError error;

    public ResponseBuilder<T> withData(T data) {
        Preconditions.checkState(error == null, "Cannot set data if error is set");
        this.data = data;
        return this;
    }

    public ResponseBuilder<T> withError(ResponseError error) {
        Preconditions.checkState(data == null, "Cannot set error if data is set");
        this.error = error;
        return this;
    }

    public ResponseObject<T> build() {
        Preconditions.checkState(data != null ^ error != null, "Either error or data must be set");
        ResponseObject<T> dto = new ResponseObject<>();
        dto.setData(data);
        dto.setError(error);
        return dto;
    }
}
