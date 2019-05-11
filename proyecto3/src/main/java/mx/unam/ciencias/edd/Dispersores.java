package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        // Aquí va su código.
        if (llave == null)
            return 0;

        if (llave.length % 4 != 0) {
            byte[] llaveAux = llave;
            llave = new byte[llaveAux.length + (4 - llaveAux.length % 4)];
            for (int i = 0; i < llave.length; i++)
                if (i >= llaveAux.length)
                    llave[i] = (byte)0x00;
                else
                    llave[i] = llaveAux[i];
        }

        int xor = 0;

        for (int i = 0; i < llave.length; i += 4)
            xor ^= combina(llave[i], llave[i+1], llave[i+2], llave[i+3]);

        return xor;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        // Aquí va su código.
        if (llave == null || llave.length == 0)
            return 0xFFFFFFFF;

        int a = 0x9E3779B9, b = 0x9E3779B9, c = 0xFFFFFFFF;
        int j = (int)(llave.length/12)*12;
        int[] abc;

        for (int i = 0; i < j; i+= 12) {
            c += combina(llave[i+11],llave[i+10],llave[i+9],llave[i+8]);
            b += combina(llave[i+7],llave[i+6],llave[i+5],llave[i+4]);
            a += combina(llave[i+3],llave[i+2],llave[i+1],llave[i]);
            abc = mezcla(a,b,c);
            a = abc[0];
            b = abc[1];
            c = abc[2];
        }

        c += llave.length;

        switch (llave.length - j) {
            case 1:
                a += combina((byte)0x00,(byte)0x00,(byte)0x00,llave[j]);
            break;

            case 2:
                a += combina((byte)0x00,(byte)0x00,llave[j+1],llave[j]);
            break;

            case 3:
                a += combina((byte)0x00,llave[j+2],llave[j+1],llave[j]);
            break;

            case 4:
                a += combina(llave[j+3],llave[j+2],llave[j+1],llave[j]);     
            break;

            case 5:
                a += combina(llave[j+3],llave[j+2],llave[j+1],llave[j]);
                b += combina((byte)0x00,(byte)0x00,(byte)0x00,llave[j+4]);
            break;

            case 6:
                a += combina(llave[j+3],llave[j+2],llave[j+1],llave[j]);
                b += combina((byte)0x00,(byte)0x00,llave[j+5],llave[j+4]);
            break;

            case 7:
                a += combina(llave[j+3],llave[j+2],llave[j+1],llave[j]);
                b += combina((byte)0x00,llave[j+6],llave[j+5],llave[j+4]);
            break;

            case 8:
                a += combina(llave[j+3],llave[j+2],llave[j+1],llave[j]);
                b += combina(llave[j+7],llave[j+6],llave[j+5],llave[j+4]);
            break;

            case 9:
                a += combina(llave[j+3],llave[j+2],llave[j+1],llave[j]);
                b += combina(llave[j+7],llave[j+6],llave[j+5],llave[j+4]);
                c += combina((byte)0x00,(byte)0x00,llave[j+8],(byte)0x00);
            break;

            case 10:
                a += combina(llave[j+3],llave[j+2],llave[j+1],llave[j]);
                b += combina(llave[j+7],llave[j+6],llave[j+5],llave[j+4]);
                c += combina((byte)0x00,llave[j+9],llave[j+8],(byte)0x00);
            break;

            case 11:
                a += combina(llave[j+3],llave[j+2],llave[j+1],llave[j]);
                b += combina(llave[j+7],llave[j+6],llave[j+5],llave[j+4]);
                c += combina(llave[j+10],llave[j+9],llave[j+8],(byte)0x00);
            break;
        }

        abc = mezcla(a,b,c);
        a = abc[0];
        b = abc[1];
        c = abc[2];
        return c;
    } 

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        // Aquí va su código.
        int djb = 5381;

        for (int i = 0; i < llave.length; i++)
            djb = djb * 33 + (llave[i] & 0xFF);

        return djb;
    }

    private static int combina(byte a, byte b, byte c, byte d) {
        return ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8) | ((d & 0xFF));
    }

    private static int[] mezcla(int a,int b, int c) {
        int[] abc = new int[3];
        a -= b;
        a -= c;
        a ^= (c >>> 13);
        b -= c;
        b -= a;
        b ^= (a << 8);
        c -= a;
        c -= b;
        c ^= (b >>> 13);
        a -= b;
        a -= c;
        a ^= (c >>> 12);
        b -= c;
        b -= a;
        b ^= (a << 16);
        c -= a;
        c -= b;
        c ^= (b >>> 5);
        a -= b;
        a -= c;
        a ^= (c >>> 3);
        b -= c;
        b -= a;
        b ^= (a << 10);
        c -= a;
        c -= b;
        c ^= (b >>> 15);
        abc[0] = a;
        abc[1] = b;
        abc[2] = c;
        return abc;
    }
}