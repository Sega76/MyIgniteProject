package stepik;


public class AsciiCharSequence implements CharSequence {
    private byte[] ascii;


    public AsciiCharSequence(byte[] arr) {
        this.ascii=arr;
    }

    @Override
    public int length() {
        return ascii.length;
    }

    @Override
    public char charAt(int index) {
        return (char)ascii[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        byte[] subChar = new byte[end-start];
        int j=0;
        for (int i = start; i < end ; i++) {
            subChar[j] = this.ascii[i];
            j++;
        }

        return new AsciiCharSequence(subChar);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b:ascii){
            sb.append((char)b);
        }
        return "AsciiCharSequence{" +
                "ascii=" + sb.toString() +
                '}';
    }
}
