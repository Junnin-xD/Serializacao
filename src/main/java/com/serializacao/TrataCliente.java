package com.serializacao;

import java.net.Socket;
import java.util.ArrayList;

import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.moandjiezana.toml.Toml;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TrataCliente implements Runnable {
		
		private Socket soquete_cliente;
		private ObjectOutputStream saida;
		private ObjectInputStream entrada;
		private ArrayList<Mensagem> mensagens;
		
		public TrataCliente(Socket soquete_cliente, ArrayList<Mensagem> mensagens) throws Exception {
			super();
			this.soquete_cliente = soquete_cliente;
			this.saida = new ObjectOutputStream(this.soquete_cliente.getOutputStream()); 
			this.entrada = new ObjectInputStream(this.soquete_cliente.getInputStream());
			this.mensagens = mensagens;
		}
		
		public void enviar_mensagem(Object mensagem) throws Exception {
			this.saida.writeObject(mensagem);
		}
		
		public Object receber_mensagem() throws Exception {
			return this.entrada.readObject();
		}
		
		public void finalizar() throws IOException {
			this.soquete_cliente.close();
		}
		
		@Override
		public void run() {
			try {
                System.out.println("-------------------------------------------------SERVIDOR---------------------------------------------------------");
				String mensagemJson = (String) receber_mensagem();
                mensagens.add(desserializaJson(mensagemJson));
                System.out.println("------------------------------------------------------------------------------------------------------------------");
                System.out.println("JSON: " + mensagemJson.toString());

                String mensagemXml = (String) receber_mensagem();
                mensagens.add(desserializaXml(mensagemXml));
                System.out.println("------------------------------------------------------------------------------------------------------------------");
				System.out.println("XML: " +mensagemXml.toString());

                String mensagemYaml = (String) receber_mensagem();
                mensagens.add(desserializaYaml(mensagemYaml));
                System.out.println("------------------------------------------------------------------------------------------------------------------");
                System.out.println("YAML: " +mensagemYaml.toString());

                String mensagemToml = (String) receber_mensagem();
                mensagens.add(desserializaToml(mensagemToml));
                System.out.println("------------------------------------------------------------------------------------------------------------------");
                System.out.println("TOML: " +mensagemToml.toString());

                String mensagemCsv = (String) receber_mensagem();
                mensagens.add(desserializaCsv(mensagemCsv));
                System.out.println("------------------------------------------------------------------------------------------------------------------");
                System.out.println("CSV: " +mensagemCsv.toString());
                System.out.println("------------------------------------------------------------------------------------------------------------------");
				
				finalizar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

        public static Mensagem desserializaJson(String x) {
            Gson gson = new Gson();
            Mensagem mensagem = gson.fromJson(x, Mensagem.class);
            return mensagem;
        }
    
        public static Mensagem desserializaXml(String x) throws JsonMappingException, JsonProcessingException {
    
            XmlMapper xmlMapper = new XmlMapper();
            Mensagem mensagemXml = xmlMapper.readValue(x, Mensagem.class);
            return mensagemXml;
        }
    
        public static Mensagem desserializaYaml(String x) {
            Yaml yaml = new Yaml();
    
            Mensagem mensagemYaml = yaml.load(x);
    
            return mensagemYaml;
        }
    
        public static Mensagem desserializaToml(String x) {
            Toml toml = new Toml();
    
            Mensagem mensagemToml = toml.read(x).to(Mensagem.class);
    
            return mensagemToml;
        }
    
        public static Mensagem desserializaCsv(String x) throws JsonMappingException, JsonProcessingException {
            CsvMapper csvMapper = new CsvMapper();
    
            CsvSchema schema = csvMapper.schemaFor(Mensagem.class);
            Mensagem mensagemCsv = csvMapper.readerFor(Mensagem.class).with(schema).readValue(x);
    
            return mensagemCsv;
        }
	}
