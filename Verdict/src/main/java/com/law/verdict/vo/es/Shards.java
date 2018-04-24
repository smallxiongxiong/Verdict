package com.law.verdict.vo.es;

public class Shards {
	private Integer total;
	private Integer successful;
	private Integer skipped;
	private Integer failed;
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getSuccessful() {
		return successful;
	}
	public void setSuccessful(Integer successful) {
		this.successful = successful;
	}
	public Integer getSkipped() {
		return skipped;
	}
	public void setSkipped(Integer skipped) {
		this.skipped = skipped;
	}
	public Integer getFailed() {
		return failed;
	}
	public void setFailed(Integer failed) {
		this.failed = failed;
	}

}
