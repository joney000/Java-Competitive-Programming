class Developer{
	private int age;
	
	public Developer(int age){
		this.age = age;
	}

	void setAge(int age){
		this.age = age;
	}
	public int getAge(){
		return age;
	}
}

class DevComparator implements Comparator<Developer> {
    @Override
    public int compare(Developer firstValue, Developer secondValue){
        return Integer.compare(firstValue.getAge(), secondValue.getAge());
    }
}

public class ComparatorSnippet{

	public static void main(String... args)throws Exception{
		// way - 1
		// better if sorting criteria keeps changing
		Developer[] devs = Collections.sort(listDevs, new Comparator<Developer>() {
			@Override
			public int compare(Developer o1, Developer o2) {
				return o1.getAge() - o2.getAge();
			}
		});
		// way - 2
		devs = Collections.sort(listDevs, (Developer a , Developer b) -> Integer.compare(a.getAge(), b.getAge()));
		// way - 3 
		devs = Collections.sort(listDevs, new DevComparator());
	}
}