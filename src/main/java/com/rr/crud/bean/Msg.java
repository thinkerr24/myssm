package com.rr.crud.bean;

import java.util.HashMap;
import java.util.Map;

/*
 * ����ͨ�õ���
 */
public class Msg {
	// ״̬�� 100-�ɹ� 200-ʧ��
	private int code;
	// ��ʾ��Ϣ
	private String msg;
	// �û����ظ������������
	private Map<String, Object> jdata = new HashMap<String, Object>();
	
	public static Msg success() {
		Msg result = new Msg();
		result.setCode(100);
		result.setMsg("����ɹ�!");
		return result;
	}
	
	public static Msg failure() {
		Msg result = new Msg();
		result.setCode(200);
		result.setMsg("����ʧ��!");
		return result;
	}
	
	public Msg add(String key, Object value) {
		getJdata().put(key, value);
		return this;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getJdata() {
		return jdata;
	}
	public void setJdata(Map<String, Object> jdata) {
		this.jdata = jdata;
	}
	
	
}
