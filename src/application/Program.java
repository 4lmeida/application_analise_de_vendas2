package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			List<Sale> list = new ArrayList<>();
			
			String lines = br.readLine();
			while(lines != null) {
				String[] fields = lines.split(",");
				list.add(new Sale(
						Integer.parseInt(fields[0]), 
						Integer.parseInt(fields[1]), 
						fields[2], 
						Integer.parseInt(fields[3]), 
						Double.parseDouble(fields[4])));
				
				lines = br.readLine();
			}
		    
			Map<String, Double> mapList = new HashMap<>();
			for (Sale li : list) {
				mapList.put(li.getSeller(), 0.0);
			}
			
			for (String seller : mapList.keySet() ) {
				double valueTotal = list.stream()
						.filter(x -> seller.toUpperCase().equals(x.getSeller().toUpperCase()))
						.map(x -> x.getTotal())
						.reduce(0.0, (x, y) -> x + y);
				
				mapList.put(seller, valueTotal);
				
			}
			
			System.out.println();
			System.out.println("Total de vendas por vendedor:");
			for (String seller : mapList.keySet()) {
				System.out.println(seller + " - R$ " + String.format("%.2f", mapList.get(seller)));
			}
			
		}
		catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		sc.close();

	}

}
