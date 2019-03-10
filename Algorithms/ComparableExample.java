
class Person implements Comparable<Person>{
	private int start, end, salary;
	
	public Person(int start, int end, int salary){
		this.start  = start;
		this.end 		= end;
		this.salary = salary;
	}

	@Override
	public int compareTo(Person that){
		return this.start - that.start;
	}
}
