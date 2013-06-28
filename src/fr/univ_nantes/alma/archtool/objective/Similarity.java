package fr.univ_nantes.alma.archtool.objective;

public class Similarity
{
    private String compOne;
    private String compTwo;
    private int[][] matrix;
    private Boolean calculated = false;

    public Similarity(final String a, final String b)
    {
        if (((a.length() > 0) || !a.isEmpty())
                || ((b.length() > 0) || !b.isEmpty()))
        {
            this.compOne = a;
            this.compTwo = b;
        }
    }

    public int[][] getMatrix()
    {
        this.setupMatrix();
        return this.matrix;
    }

    public int getSimilarity()
    {
        if (!this.calculated)
        {
            this.setupMatrix();
        }

        return this.matrix[this.compOne.length()][this.compTwo.length()];
    }

    private void setupMatrix()
    {
        int cost = -1;
        int del, sub, ins;

        this.matrix =
                new int[this.compOne.length() + 1][this.compTwo.length() + 1];

        for (int i = 0; i <= this.compOne.length(); i++)
        {
            this.matrix[i][0] = i;
        }

        for (int i = 0; i <= this.compTwo.length(); i++)
        {
            this.matrix[0][i] = i;
        }

        for (int i = 1; i <= this.compOne.length(); i++)
        {
            for (int j = 1; j <= this.compTwo.length(); j++)
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

        this.calculated = true;
    }

    private void displayMatrix()
    {
        System.out.println(" " + this.compOne);
        for (int y = 0; y <= this.compTwo.length(); y++)
        {
            if ((y - 1) < 0)
            {
                System.out.print(" ");
            } else
            {
                System.out.print(this.compTwo.charAt(y - 1));
            }
            for (int x = 0; x <= this.compOne.length(); x++)
            {
                System.out.print(this.matrix[x][y]);
            }
            System.out.println();
        }
    }

    private int minimum(final int d, final int i, final int s)
    {
        int m = Integer.MAX_VALUE;

        if (d < m)
        {
            m = d;
        }
        if (i < m)
        {
            m = i;
        }
        if (s < m)
        {
            m = s;
        }

        return m;
    }

    private int minimum(final int d, final int t)
    {
        int m = Integer.MAX_VALUE;

        if (d < m)
        {
            m = d;
        }
        if (t < m)
        {
            m = t;
        }

        return m;
    }
}
