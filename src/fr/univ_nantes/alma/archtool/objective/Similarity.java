package fr.univ_nantes.alma.archtool.objective;

public class Similarity
{
    private final static double MIN_RATIO = 0.6;

    private String compOne;
    private String compTwo;
    private int[][] matrix;

    public boolean similar(final String a, final String b)
    {
        this.compOne = a;
        this.compTwo = b;

        this.setupMatrix();

        final double nbDiffs =
                this.matrix[this.compOne.length()][this.compTwo.length()];
        final double minLen =
                this.minimum(this.compOne.length(), this.compTwo.length());
        
        final double ratio = 1 - (nbDiffs / minLen);

        return (ratio > Similarity.MIN_RATIO);
    }

    private void setupMatrix()
    {
        int cost = -1;
        int del, sub, ins;

        this.matrix =
                new int[this.compOne.length() + 1][this.compTwo.length() + 1];

        for (int i = 0 ; i <= this.compOne.length() ; i++)
        {
            this.matrix[i][0] = i;
        }

        for (int i = 0 ; i <= this.compTwo.length() ; i++)
        {
            this.matrix[0][i] = i;
        }

        for (int i = 1 ; i <= this.compOne.length() ; i++)
        {
            for (int j = 1 ; j <= this.compTwo.length() ; j++)
            {
                if (this.compOne.charAt(i - 1) == this.compTwo.charAt(j - 1))
                {
                    cost = 0;
                }
                else
                {
                    cost = 1;
                }

                del = this.matrix[i - 1][j] + 1;
                ins = this.matrix[i][j - 1] + 1;
                sub = this.matrix[i - 1][j - 1] + cost;

                this.matrix[i][j] = this.minimum(del, ins, sub);

                if ((i > 1) && (j > 1)
                        && (this.compOne.charAt(i - 1) == this.compTwo
                                .charAt(j - 2))
                        && (this.compOne.charAt(i - 2) == this.compTwo
                                .charAt(j - 1)))
                {
                    this.matrix[i][j] = this.minimum(this.matrix[i][j],
                            this.matrix[i - 2][j - 2]
                                    + cost);
                }
            }
        }
    }

    private int minimum(final int a, final int b, final int c)
    {
        int m = a;

        if (m > b)
        {
            m = b;
        }

        if (m > c)
        {
            m = c;
        }

        return m;
    }

    private int minimum(final int a, final int b)
    {
        return (a < b) ? a : b;
    }
}
