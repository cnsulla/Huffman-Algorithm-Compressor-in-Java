public class HuffmanCompressor
{
    HeapToHuff heap;
    SerializeTree en;
    DeserializeTree de;
    HuffmanNode huffTreeTurned;
    CompressImage comp;

    public HuffmanCompressor()
    {
        heap = new HeapToHuff("test6");
        HuffmanNode huffTreeOrig = heap.HuffmanConvert();
        
        
        en = new SerializeTree(huffTreeOrig);
        System.out.println("Tree Serialized \n-------------");

        de = new DeserializeTree("tree2.fl");
        huffTreeTurned = de.returnTree();
        System.out.println("Tree Deserialized \n-----------");
        de.printInorder(huffTreeTurned);

        System.out.println("Image file writing.. \n-----------");
        comp = new CompressImage(huffTreeOrig, huffTreeOrig.freq, "test6");
        System.out.println("Finished.. \n-----------");
    }   

    public static void main(String[] args)
    {
        new HuffmanCompressor();
    }
}