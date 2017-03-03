	// Two Pointers a[i] + a[j]==k unique pairs i != j	
    static int NumberOfPairs(int[] a, long k) {
        int n = a.length;
        int arr[] = new int[n+2];
      for(int i = 1 ; i<= n ; i++)arr[i] = a[i-1];
        int i = 1 , j = n;
        
        
        Arrays.sort(arr , 1 , n +1);
        int ans = 0;
        while(true){
                if(i==n+1 || j== 0  || j < i)break;
                
                if( arr[i] + arr[j] <= k ){
                
                    if(arr[i] + arr[j] == k && i != j){
                        if(arr[i] != arr[i-1] && arr[j] != arr[j+1]){ans++;System.out.println("i="+i+" j="+j + " ans= "+ans);}
                        
                    }   
                    i++; 
                }else{
                    
                    j--;
                            
                }
                                                
        }
        return ans;

    }
            
            
