package com.xxdxxs.utils;

import com.xxdxxs.enums.Operator;
import com.xxdxxs.service.ConditionFilter;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Convertable<K, V> {

    default Object get(K key) {
        return getConvertable(key);
    }

    default Optional<String> getString(K key) {
        return ConvertUtil.STRING.convert(get(key));
    }

    default Optional<List<?>> getList(K key) {
        return Optional.of((List<Object>) getConvertable(key));
    }

    default Optional<Map<String, ?>> getMap(K key) {
        return Optional.of((Map<String, ?>) getConvertable(key));
    }

    default Optional<Integer> getInteger(K key) {
        return ConvertUtil.INTEGER.convert(get(key));
    }

    default Optional<Double> getDouble(K key) {
        return ConvertUtil.DOUBLE.convert(get(key));
    }

    default Optional<Float> getFloat(K key) {
        return ConvertUtil.FLOAT.convert(get(key));
    }

    default Optional<Long> getLong(K key) {
        return ConvertUtil.LONG.convert(get(key));
    }

    default Optional<Date> getDate(K key) {
        return ConvertUtil.DATE.convert(get(key));
    }

    default Optional<String> getDateByFormat(K key, String format) {
        return ConvertUtil.DATE.convertByFormat(get(key), format);
    }

    default Optional<String> getDateByFormat(K key) {
        return ConvertUtil.DATE.convertByFormat(get(key));
    }

    default void ifPresentList(K key, BiConsumer<K, List> biConsumer) {
        Assert.notNull(biConsumer, "callback unprovided");
        getList(key).ifPresent(x -> biConsumer.accept(key, x));
    }

    default void ifPresentList(K key, Consumer<List> consumer) {
        Assert.notNull(consumer, "callback unprovided");
        getList(key).ifPresent(x -> consumer.accept(x));
    }

    default void ifPresentString(K key, BiConsumer<K, String> biConsumer) {
        Assert.notNull(biConsumer, "callback unprovided");
        getString(key).ifPresent(x -> biConsumer.accept(key, x));
    }

    default void ifPresentString(K key, Consumer<String> consumer) {
        Assert.notNull(consumer, "callback unprovided");
        getString(key).ifPresent(x -> consumer.accept(x));
    }

    default void ifPresentString(K key, ConditionFilter filter, Operator operator) {
        ifPresentString(key, x -> filter.equal((String) key, x));
    }

    default void ifPresentInteger(K key, BiConsumer<K, Integer> biConsumer) {
        Assert.notNull(biConsumer, "callback unprovided");
        getInteger(key).ifPresent(x -> biConsumer.accept(key, x));
    }

    default void ifPresentInteger(K key, Consumer<Integer> consumer) {
        Assert.notNull(consumer, "callback unprovided");
        getInteger(key).ifPresent(x -> consumer.accept(x));
    }

    default void ifPresentLong(K key, BiConsumer<K, Long> biConsumer) {
        Assert.notNull(biConsumer, "callback unprovided");
        getLong(key).ifPresent(x -> biConsumer.accept(key, x));
    }

    default void ifPresentLong(K key, Consumer<Long> consumer) {
        Assert.notNull(consumer, "callback unprovided");
        getLong(key).ifPresent(x -> consumer.accept(x));
    }

    default void ifPresentDouble(K key, BiConsumer<K, Double> biConsumer) {
        Assert.notNull(biConsumer, "callback unprovided");
        getDouble(key).ifPresent(x -> biConsumer.accept(key, x));
    }

    default void ifPresentDouble(K key, Consumer<Double> consumer) {
        Assert.notNull(consumer, "callback unprovided");
        getDouble(key).ifPresent(x -> consumer.accept(x));
    }

    default void ifPresentFloat(K key, BiConsumer<K, Float> biConsumer) {
        Assert.notNull(biConsumer, "callback unprovided");
        getFloat(key).ifPresent(x -> biConsumer.accept(key, x));
    }

    default void ifPresentFloat(K key, Consumer<Float> consumer) {
        Assert.notNull(consumer, "callback unprovided");
        getFloat(key).ifPresent(x -> consumer.accept(x));
    }

    default void ifPresentDate(K key, BiConsumer<K, Date> biConsumer) {
        Assert.notNull(biConsumer, "callback unprovided");
        getDate(key).ifPresent(x -> biConsumer.accept(key, x));
    }

    default void ifPresentDate(K key, Consumer<Date> consumer) {
        Assert.notNull(consumer, "callback unprovided");
        getDate(key).ifPresent(x -> consumer.accept(x));
    }

    default public void callBackUtil(Optional<K> optional, Consumer<K> consumer) {
        optional.ifPresent(x -> consumer.accept(x));
    }

    Object getConvertable(K key);

}
