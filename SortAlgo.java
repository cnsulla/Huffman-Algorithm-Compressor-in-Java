public class SortAlgo
{
	// public HuffmanNode[] sort(HuffmanNode arr[], int l, int r){ 
	// 	if (l < r) 
	// 	{ 
	// 		// Find the middle point 
	// 		HuffmanNode m = (l+r)/2; 
  
	// 		// Sort first and second halves 
	// 		sort(arr, l, m);
	// 		sort(arr , m+1, r);
  
	// 		// Merge the sorted halves 
	// 		merge(arr, l, m, r);
	// 	} 
	// 	return arr;
	// }

	// public void merge(HuffmanNode arr[], int l, int m, int r) { 
	// 	// Find sizes of two subarrays to be merged 
		
	// 	int n1 = m - l + 1; 
	// 	int n2 = r - m; 
  
	// 	/* Create temp arrays */
	// 	HuffmanNode left[] = new HuffmanNode [n1]; 
	// 	HuffmanNode right[] = new HuffmanNode [n2]; 
  
	// 	/*Copy data to temp arrays*/
	// 	for (int i=0; i<n1; ++i) 
	// 		left[i] = arr[l + i]; 
	// 	for (int j=0; j<n2; ++j) 
	// 		right[j] = arr[m + 1+ j]; 
  
  
	// 	/* Merge the temp arrays */
  
	// 	// Initial indexes of first and second subarrays 
	// 	int i = 0, j = 0; 
  
	// 	// Initial index of merged subarry array 
	// 	int k = l; 

	// 	int[] nodearr = arr;
	// 	while (i < n1 && j < n2) 
	// 	{ 
	// 		if (nodearr[i] > right[j])
	// 		{ 
	// 			arr[k] = left[i]; 
	// 			i++; 
	// 		} 
	// 		else
	// 		{ 
	// 			arr[k] = right[j]; 
	// 			j++; 
	// 		} 
	// 		k++; 
	// 	}

	// 	while (i < n1) 
	// 	{ 
	// 		arr[k] = left[i]; 
	// 		i++; 
	// 		k++; 
	// 	}

	// 	while (j < n2) 
	// 	{ 
	// 		arr[k] = right[j]; 
	// 		j++; 
	// 		k++; 
	// 	} 
	// }
	// public int[] mergeSort(int[] array){
	// 	if(array.length <= 1){
	// 		return array;
	// 	}
	// 	int indexToSplitArray = array.length/2;

	// 	int[] tempArray = new int[indexToSplitArray];
	// 	for(int i = 0; i < indexToSplitArray; i++){
	// 		tempArray[i] = array[i];
	// 	}
	// 	int[] leftArray = mergeSort(tempArray);

	// 	tempArray = new int[array.length - indexToSplitArray];
	// 	int j = 0;
	// 	for(int i = indexToSplitArray; i < array.length; i++){
	// 		tempArray[j++] = array[i];
	// 	}
		
	// 	int[] rightArray = mergeSort(tempArray);
	// 	return merge(leftArray, rightArray);
	// }

	// public int[] merge(int[] leftArray, int[] rightArray){
	// 	int leftIndex = 0;
	// 	int rightIndex = 0;
	// 	int[] tempArray;
	// 	int[] orderedArray = new int[0];

	// 	while(leftIndex < leftArray.length && rightIndex < rightArray.length){
	// 		if(leftArray[leftIndex] <= rightArray[rightIndex]){
	// 			tempArray = new int[orderedArray.length+1];
	// 			for(int i = 0; i < orderedArray.length; i++){
	// 				tempArray[i] = orderedArray[i];
	// 			}
	// 			tempArray[orderedArray.length] = leftArray[leftIndex];
	// 			orderedArray = tempArray;
	// 			leftIndex++;
	// 		}
	// 		else{
	// 			tempArray = new int[orderedArray.length+1];
	// 			for(int i = 0; i < orderedArray.length; i++){
	// 				tempArray[i] = orderedArray[i];
	// 			}
	// 			tempArray[orderedArray.length] = rightArray[rightIndex];
	// 			orderedArray = tempArray;
	// 			rightIndex++;
	// 		}

	// 	}

	// 	if(leftIndex < leftArray.length){
	// 		while(leftIndex < leftArray.length){
	// 			tempArray = new int[orderedArray.length+1];
	// 			for(int i = 0; i < orderedArray.length; i++){
	// 				tempArray[i] = orderedArray[i];
	// 			}
	// 			tempArray[orderedArray.length] = leftArray[leftIndex];
	// 			orderedArray = tempArray;
	// 			leftIndex++;
	// 		}
	// 	}

	// 	if(rightIndex < rightArray.length){
	// 		while(rightIndex < rightArray.length){
	// 			tempArray = new int[orderedArray.length+1];
	// 			for(int i = 0; i < orderedArray.length; i++){
	// 				tempArray[i] = orderedArray[i];
	// 			}
	// 			tempArray[orderedArray.length] = rightArray[rightIndex];
	// 			orderedArray = tempArray;
	// 			rightIndex++;
	// 		}
	// 	}

	// 	int[] arr = new int[orderedArray.length + 1];
	// 	for (int i=0; i<orderedArray.length; i++){
	// 		arr[i] = orderedArray[i];
	// 	}

	// 	return arr;
	// }
}