package fr.univ_nantes.alma.archtool.dot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Facade;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.File;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;
import fr.univ_nantes.alma.archtool.utils.Pair;

public class DotGraph
{
	private StringBuffer graph = new StringBuffer();
	
	public String getGraph()
	{
		return this.graph.toString();
	}
	
	public void createGraph(SourceCode sourceCode)
    {
        this.graph.setLength(0);        
        this.graph.append("digraph G{graph [style=\"rounded,"
                + " filled\", color=black, fillcolor=\"#CCE5FF\", fontsize=11, "
                + "fontname=Helvetica];node [shape=plaintext];");
        
        int fileIndex = 0;
                
        for(File sourceFile : sourceCode.getSourceFiles())
        {           
            this.graph.append("subgraph ");
            this.graph.append("cluster" + fileIndex + "{");
            this.graph.append("label = \"" + sourceFile.getPath() + "\";");
                
            for(Function f : sourceCode.getFileFunctions(sourceFile))
            {
                this.graph.append(f.getName() + "[fontcolor=blue];");
            }
                
            for(ComplexType t : sourceCode.getFileComplexTypes(sourceFile))
            {
                this.graph.append(t.getName() + "[fontcolor=green];");
            }
                
            for(GlobalVariable v : 
                sourceCode.getFileGlobalVariables(sourceFile))
            {
                this.graph.append(v.getName() + "[fontcolor=red];");
            }

            this.graph.append("}");
            fileIndex++;
        }
                        
        for(Function f : sourceCode.getCoreFunctions())
        {
            for(Entry<Function, Integer> fUse : 
                f.getCoreCalledFunctions().entrySet())
            {
                this.graph.append(f.getName() + " -> " +
                        fUse.getKey().getName() + "[label=\"" + fUse.getValue()
                        + "\"];");
            }
            
            for(Entry<ComplexType, Integer> tUse : 
                f.getCoreComplexTypes().entrySet())
            {
                this.graph.append(f.getName() + " -> " +
                        tUse.getKey().getName() + "[label=\"" + tUse.getValue()
                        + "\"];");
            }
            
            for(Entry<GlobalVariable, Integer> vUse : 
                f.getCoreGlobalVariables().entrySet())
            {
                this.graph.append(f.getName() + " -> " +
                        vUse.getKey().getName() + "[label=\"" + vUse.getValue()
                        + "\"];");
            }
        }
            
        for(GlobalVariable v : sourceCode.getCoreGlobalVariables())
        {
            if(v.getType().isComplex() &&
                    sourceCode.getCoreComplexTypes().contains(
                    (ComplexType) v.getType()))
            {
                this.graph.append(v.getName() + " -> " +
                       v.getType().getName() + ";");
            }
        }

        this.graph.append("}");
    }
	
	public void createGraph(Architecture architecture)
	{
	    Map<Component, String> compToIds = new HashMap<Component, String>();
	    Map<Connector, String> conToIds = new HashMap<Connector, String>();
	    Map<Interface, String> itfToIds = new HashMap<Interface, String>();
        Map<Facade, String> facToIds = new HashMap<Facade, String>();
	    
	    this.graph.setLength(0);	    
		this.graph.append("digraph G{concentrate=true;graph [style=\"rounded,"
				+ " filled\", color=black, fillcolor=\"#CCE5FF\", fontsize=11, "
				+ "fontname=Helvetica];node [shape=plaintext];");
		
		int nodeIndex = 0;
		String nodeName;
		int compIndex = 0;
		int conIndex = 0;
		int itfIndex = 0;
		int facIndex = 0;
		String clusterName;
		
		for(Component c : architecture.getComponents())
		{
			Set<Function> functionsInInterfaces = new HashSet<Function>();
			Set<ComplexType> typesInInterfaces = new HashSet<ComplexType>();
			Set<GlobalVariable> globalsInInterfaces =
					new HashSet<GlobalVariable>();
			
			this.graph.append("subgraph ");
			
			clusterName = "cluster_comp" + compIndex;
			compToIds.put(c, clusterName);
			
			this.graph.append(clusterName + "{");
			this.graph.append("label = \"Composant" + compIndex++ + "\";");
						
			for(Interface provided : c.getProvidedInterfaces())
			{
				this.graph.append("subgraph ");
				clusterName = "cluster_itf" + itfIndex++;
				itfToIds.put(provided, clusterName);
				this.graph.append(clusterName + "{");
				this.graph.append("label = \"Fournit\";");
				this.graph.append("fillcolor = \"#FFB266\";");
				
				for(Function f : provided.getFunctions())
				{
				    nodeName = "n" + nodeIndex++;
					this.graph.append(nodeName+ "[label= " 
					        + f.getName() + " ,fontcolor=blue];");
				}
				
				for(ComplexType t : provided.getComplexTypes())
				{
				    nodeName = "n" + nodeIndex++;
				    this.graph.append(nodeName + "[label= " 
                            + t.getName() + " ,fontcolor=green];");
				}
				
				for(GlobalVariable v : provided.getGlobalVariables())
				{
				    nodeName = "n" + nodeIndex++;
				    this.graph.append(nodeName + "[label= " 
                            + v.getName() + " ,fontcolor=red];");
				}
				
				functionsInInterfaces.addAll(provided.getFunctions());
				typesInInterfaces.addAll(provided.getComplexTypes());
				globalsInInterfaces.addAll(provided.getGlobalVariables());
				
				this.graph.append("}");
			}
			
			// Create nodes for entities out of interfaces
			Set<Function> functionsOutInterfaces = c.getFunctions();
			Set<ComplexType> typesOutInterfaces = c.getComplexTypes();
			Set<GlobalVariable> globalsOutInterfaces = c.getGlobalVariables();
			
			functionsOutInterfaces.removeAll(functionsInInterfaces);
			typesOutInterfaces.removeAll(typesInInterfaces);
			globalsOutInterfaces.removeAll(globalsInInterfaces);
			
			for(Function f : functionsOutInterfaces)
			{
			    nodeName = "n" + nodeIndex++;
			    this.graph.append(nodeName + "[label= " 
                        + f.getName() + " ,fontcolor=blue];");
			}
			
			for(ComplexType t : typesOutInterfaces)
			{
			    nodeName = "n" + nodeIndex++;
			    this.graph.append(nodeName + "[label= " 
                        + t.getName() + " ,fontcolor=green];");
			}
			
			for(GlobalVariable v : globalsOutInterfaces)
			{
			    nodeName = "n" + nodeIndex++;
			    this.graph.append(nodeName + "[label= " 
                        + v.getName() + " ,fontcolor=red];");
			}
			
			this.graph.append("}");
		}
		
		for(Connector con : architecture.getConnectors())
		{
		    this.graph.append("subgraph ");
		    clusterName = "cluster_con" + conIndex;
		    conToIds.put(con, clusterName);
            this.graph.append(clusterName + "{");
            this.graph.append("label = \"Connecteur" + conIndex++ + "\";");
                        
            for(Facade facade : con.getFacades())
            {
                this.graph.append("subgraph ");
                clusterName = "cluster_fac" + facIndex++;
                facToIds.put(facade, clusterName);
                this.graph.append(clusterName + "{");
                this.graph.append("label = \"Façade\";");
                this.graph.append("fillcolor = \"#FFB266\";");
                
                for(Function f : facade.getFunctions())
                {
                    nodeName = "n" + nodeIndex++;
                    this.graph.append(nodeName + "[label= " 
                            + f.getName() + " ,fontcolor=blue];");
                }
                
                for(ComplexType t : facade.getComplexTypes())
                {
                    nodeName = "n" + nodeIndex++;
                    this.graph.append(nodeName + "[label= " 
                            + t.getName() + " ,fontcolor=green];");
                }
                
                for(GlobalVariable v : facade.getGlobalVariables())
                {
                    nodeName = "n" + nodeIndex++;
                    this.graph.append(nodeName + "[label= " 
                            + v.getName() + " ,fontcolor=red];");
                }
                
                this.graph.append("}");
            }
            
            for(Function f : con.getFunctions())
            {
                nodeName = "n" + nodeIndex++;
                this.graph.append(nodeName + "[label= " 
                        + f.getName() + " ,fontcolor=blue];");
            }
            
            for(ComplexType t : con.getComplexTypes())
            {
                nodeName = "n" + nodeIndex++;
                this.graph.append(nodeName + "[label= " 
                        + t.getName() + " ,fontcolor=green];");
            }
            
            for(GlobalVariable v : con.getGlobalVariables())
            {
                nodeName = "n" + nodeIndex++;
                this.graph.append(nodeName + "[label= " 
                        + v.getName() + " ,fontcolor=red];");
            }
            
            this.graph.append("}");
		}
		
		for(Pair<Connector, Component> connection : 
		    architecture.getConfiguration().getConnections())
		{
		    String componentCluster = compToIds.get(connection.second);
		    String connectorCluster = conToIds.get(connection.first);		    
		    this.graph.append(componentCluster + "->" + connectorCluster + ";");
		}
		
		this.graph.append("}");
	}	
	
	public void writeToFile(String filename)
	{
	    BufferedWriter writer = null;
	    
	    try
	    {
	        writer = new BufferedWriter(new FileWriter(filename));
	        writer.write(this.graph.toString());
	        writer.close();
	    }
	    catch(IOException e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        if(writer != null)
	        {
	            try
	            {
	                writer.close();
	            }
	            catch(IOException e)
	            {
	            }
	        }
	    }
	}
}