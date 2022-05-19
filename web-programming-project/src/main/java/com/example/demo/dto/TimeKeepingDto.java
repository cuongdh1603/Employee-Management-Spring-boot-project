package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.TimeKeeping;

public class TimeKeepingDto {
	private List<TimeKeeping> timeKeepings;
	public void addTimeKeeping(TimeKeeping timeKeeping) {
		this.timeKeepings.add(timeKeeping);
	}
	public TimeKeepingDto() {
		super();
		this.timeKeepings = new ArrayList<>();
	}
	public List<TimeKeeping> getTimeKeepings() {
		return timeKeepings;
	}
	public void setTimeKeepings(List<TimeKeeping> timeKeepings) {
		this.timeKeepings = timeKeepings;
	}
	
}
