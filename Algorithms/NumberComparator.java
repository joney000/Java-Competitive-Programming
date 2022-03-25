package Algorithms;

public class NumberComparator {
        private  int a;
        int b;

        public NumberComparator(){
            this.a = 0;
            this.b = 0;
        }

        public NumberComparator(int a,int b){
            this.a = a;
            this.b = b;
        }

        public int compareTo(Algorithms.Pair p){
            if(this.a == p.a){
                return this.b - p.b;
            }
            return this.a - p.a;
        }

        @Override
        public String toString(){
            return "number1 = " + this.a + " number2 = " + this.b;
        }
}
