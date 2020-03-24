package com.radioson.mms.bean;

/**
 * 封装返回数据的统一格式
 * @author WZW
 * @date 2019年12月26日 下午9:41:13
 *
 * @param <T>
 */
public class Result<T> {

	//响应码， 0 :成功，其他数值代表失败
	private Integer code;

	//响应信息，若status为0时，为success
	private String msg;

	//记录条数，分页用到
	private long count;

	//响应数据，使用泛型兼容不同的类型
	private T data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public String toString() {
		return "Result{"+"code="+code+", msg="+msg+", data="+data+"}";
	}

}
