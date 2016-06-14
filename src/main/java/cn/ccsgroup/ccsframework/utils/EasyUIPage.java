package cn.ccsgroup.ccsframework.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EasyUIPage implements Serializable{
	public static final String BEGIN = "begin";
	public static final String END = "end";
	public EasyUIPage(){
	}
	
	private int page = 1;
	private int begin;
	private int end;
	private int total = 0;
	private List<?> rows = new ArrayList();
    private List<?> footer = new ArrayList();

    public List<?> getFooter() {
        return footer;
    }

    public void setFooter(List<?> footer) {
        this.footer = footer;
    }

    public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getBegin() {
		return begin;
	}
	public int getEnd() {
		return end;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setPagePara(Integer rows){
		if(rows != null){
			this.begin = (this.page - 1) * rows ;
			this.end = this.page * rows;
		}
	}
}
