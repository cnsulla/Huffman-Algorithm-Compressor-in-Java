public class MinHeap
{
    private HuffmanNode[] Heap; 
    private int size; 
    private int maxsize; 
  
    private static final int FRONT = 0; 
  
    public MinHeap(int maxsize) 
    {
        this.maxsize = maxsize+1; 
        this.size = 0; 
        Heap = new HuffmanNode[this.maxsize]; 
    }

    private boolean isLeaf(int pos) 
    { 

        if (pos >= (size / 2) && pos <= size) { 
            return true; 
        } 

        if (leftChild(pos) > maxsize && rightChild(pos) > maxsize){
            return true;
        }

        return false; 
    } 

    public void insert(HuffmanNode element) 
    { 
        if (size >= maxsize) { 
            return;
        }

        if (size == 0)
        {
            Heap[size] = element;
            size++;
            return;
        }
        
        Heap[size] = element;
        int current = size;
        size++;
        while (Heap[current].freq < Heap[parent(current)].freq) { 
            swap(current, parent(current)); 
            current = parent(current); 
        }
    }

    public HuffmanNode remove() 
    {
        if (size == 0)
        {
            return null;
        }

        HuffmanNode popped = Heap[FRONT];
        Heap[FRONT] = Heap[--size];
        
        
        minHeapify(FRONT);
        return popped;
    }
  
    private int parent(int pos) 
    {
        return (pos-1)/ 2; 
    }

    public HuffmanNode getTop()
    {
        return Heap[0];
    }
  
    private int leftChild(int pos) 
    { 
        return (2 * pos); 
    } 
  
    private int rightChild(int pos) 
    { 
        return (2 * pos) + 1; 
    } 
  
  
    private void swap(int fpos, int spos) 
    { 
        HuffmanNode tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    } 
  
    private void minHeapify(int pos) 
    {
        if (!isLeaf(pos)) {
            
            if (Heap[pos].freq >
             Heap[leftChild(pos)].freq 
                || Heap[pos].freq > Heap[rightChild(pos)].freq) {

                if (Heap[leftChild(pos)].freq < Heap[rightChild(pos)].freq) {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                }

                else {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }

    public void print()
    {
        int i = 0;
        while (i < size-1 
                || Heap[i]!=null )
        {
            System.out.print(" " + Heap[i].freq +" " + Heap[i].pVal + " (index: " + i +")");
            System.out.println(); 
            i++;
        }
    }

    public void minHeap() 
    { 
        for (int pos = (size / 2); pos >= 1; pos--) { 
            minHeapify(pos); 
        }
    }

    
    
}
