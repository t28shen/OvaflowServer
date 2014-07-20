package com.ovaflow.server.dto;

public class Avatar {
		private int avatarid;
		private String avatarname;
		private String poniter;
		private int price;
		
		public Avatar(int avatarid, String avatarname, int price, String poniter){
			this.avatarid = avatarid;
			this.avatarname = avatarname;
			this.poniter = poniter;
			this.price = price;
		}
		
		public int getID() {
			return avatarid;
		}
		
		public String getName() {
			return avatarname;
		}
		
		public String getPoniter() {
			return poniter;
		}
		
		public int getPrice() {
			return price;
		}
	}
