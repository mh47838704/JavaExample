package com.mh.JavaExample.stream;

import java.util.AbstractList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 不可变数组，用于测试流处理
 * @param <E>
 */
public class ImmutableArrayList<E> extends AbstractList<E> implements List<E> {

    /** 元素存储数组 */
    private final Object[] elements;

    public ImmutableArrayList(Object[] data, Object[] tags){
        int dataSize = data.length;
        int tagSize = tags.length;
        if(dataSize != tagSize){
            throw new IllegalArgumentException("Array size not equal data size:"
                    +dataSize+",tag length:"+tagSize);
        }
        this.elements = new Object[2*dataSize];
        for (int i = 0, j = 0; i < dataSize; i++){
            elements[j++] = data[i];
            elements[j++] = tags[i];
        }
    }

    @Override
    public Stream<E> stream() {
        // 普通流
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public Stream<E> parallelStream() {
        // 并发流
        return StreamSupport.stream(spliterator(), true);
    }

    @Override
    public Spliterator<E> spliterator() {
        return new ImmutableSpliterator<>(elements, 0, elements.length);
    }

    static final class ImmutableSpliterator<E> implements Spliterator<E>{

        private Object[] array;
        private int origin;
        private final int fence;

        ImmutableSpliterator(Object[] array, int origin, int fence){
            this.array = array;
            this.origin = origin;
            this.fence = fence;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            // 1、自定义遍历方式
//            for (; origin < fence; origin++){
//                action.accept((E)array[origin]);
//            }
            // 2、tryAdvance
            do { }while (tryAdvance(action));
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> action) {
            // 数据访问限制判断
            if(origin < fence){
                action.accept((E)array[origin]);
                origin += 2;
                return true;
            }
            return false;
        }

        @Override
        public Spliterator<E> trySplit() {
            // 任务粒度划分判读
            int lo = origin;
            int mid = ((lo + fence) >>> 1) & ~1;
            if(lo < mid){
                origin = mid;
                return new ImmutableSpliterator<>(array, lo, mid);
            }
            return null;
        }

        @Override
        public long estimateSize() {

            return (long)((fence - origin) / 2);
        }

        @Override
        public int characteristics() {
            return ORDERED | SIZED | IMMUTABLE | SUBSIZED;
        }
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return (E)elements[index];
    }

    @Override
    public int size() {
        return elements.length;
    }

    private void rangeCheck(int index){
        if(index >= elements.length){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index){
        return "Index:"+index+",Size:"+elements.length;
    }
}
