
class Person implements Comparable<Person>{
	int start,end,c;
	public Person(int start,int end,int c){
		this.start=start;
		this.end=end;
		this.c=c;
	}
	
	public int compareTo(Person p){
		return this.start - p.start;
	}
}
