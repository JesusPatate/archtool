package fr.univ_nantes.alma.archtool.parsing.specifier;


public class NullSpecifier extends DeclarationSpecifier
{
	public NullSpecifier()
	{
		this.isNull = true;
	}
	
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithNull(this);
    }
    
    @Override
    public String toString()
    {
        return String.format("null specifier");
    }
}