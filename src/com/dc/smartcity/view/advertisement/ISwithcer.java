package com.dc.smartcity.view.advertisement;

public interface ISwithcer<T> {

    /**
     * ��ȡͼƬ�л����������URL����.����ת�����ֲ�ͬ��
     *
     * @param data
     * @return
     */
    public abstract String getUrlString(T data);

}
