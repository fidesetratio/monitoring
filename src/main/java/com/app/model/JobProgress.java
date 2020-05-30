package com.app.model;

public class JobProgress {

		private String status;
		private double  total;
		private double  progress;
		private double  percentage;


		private String filename;
		
		public JobProgress() {
			total = -1;
			progress = -1;
			percentage = 0;
		}


		public String getStatus() {
			return status;
		}


		public void setStatus(String status) {
			this.status = status;
		}


		public double  getTotal() {
			return total;
		}


		public void setTotal(double  total) {
			this.total = total;
		}


		public double  getProgress() {
			return progress;
		}


		public void setProgress(double  progress) {
			this.progress = progress;
		}


		public double  getPercentage() {
			return percentage;
		}


		public void setPercentage(double  percentage) {
			this.percentage = percentage;
		}
		public String getFilename() {
			return filename;
		}


		public void setFilename(String filename) {
			this.filename = filename;
		}

		
}
