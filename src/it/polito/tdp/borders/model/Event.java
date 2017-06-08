package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{

	//essendoci un solo tipo di evento (INGRESSO nello stato) no event Type
	private int num;
	private Country country;
	private int t;
	/**
	 * @param num
	 * @param country
	 * @param t
	 */
	public Event(int num, Country country, int t) {
		super();
		this.num = num;
		this.country = country;
		this.t = t;
	}
	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	/**
	 * @return the t
	 */
	public int getT() {
		return t;
	}
	/**
	 * @param t the t to set
	 */
	public void setT(int t) {
		this.t = t;
	}
	@Override
	public int compareTo(Event other) {
		// TODO Auto-generated method stub
		return this.t-other.getT();
	}





}



