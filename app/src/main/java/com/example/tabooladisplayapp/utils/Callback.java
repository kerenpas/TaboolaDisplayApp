package com.example.tabooladisplayapp.utils;

import java.util.List;


public interface Callback<T> {

        void onSuccess(T value);
        void onError(Throwable error);
}
