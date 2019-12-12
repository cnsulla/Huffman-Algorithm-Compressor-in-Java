class HuffmanNode
{
    int freq;
    int pVal;

    HuffmanNode left;
	HuffmanNode right;

	int bitString=0;

	public HuffmanNode(int pix)
	{
		this.pVal = pix;
	}

	public HuffmanNode(int pix, int fre)
	{
		this.pVal = pix;
		this.freq = fre;
	}

	public int getBitString()
	{
		return this.bitString;
	}

	public void setBitString(int input)
	{
		this.bitString = input;
	}

	public void setRight(HuffmanNode rightV)
	{
		this.right = rightV;
	}

	public HuffmanNode getRight()
	{
		return this.right;
	}

	public void setLeft(HuffmanNode leftV)
	{
		this.left = leftV;
	}

	public HuffmanNode getLeft()
	{
		return this.left;
	}

	public int getFreq()
	{
		return this.freq;
	}

	public int getPVal()
	{
		return this.pVal;
	}

}