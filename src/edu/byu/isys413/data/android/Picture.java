package edu.byu.isys413.data.android;

/**
 * A POJO for containing a picture.
 * 
 * @author mswensen
 */
public class Picture {
	
	private String guid;
	private String caption;
	private String data;
	private boolean selected;
	
	/**
	 * @param guid
	 * @param caption
	 */
	public Picture(String guid, String caption) {
		this.guid = guid;
		this.caption = caption;
		this.data = null;
		this.selected = false;
	}
	
	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}
	
	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	
	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return caption;
	}
}
