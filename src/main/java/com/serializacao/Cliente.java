package com.serializacao;

import java.net.Socket;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.moandjiezana.toml.TomlWriter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Cliente {
	
	private Socket soquete;
	private ObjectOutputStream saida;
	private ObjectInputStream entrada;
	public Cliente(String endereco, int porta) throws Exception {
		super();
		this.soquete = new Socket(endereco, porta);
		this.saida = new ObjectOutputStream(this.soquete.getOutputStream()); 
		this.entrada = new ObjectInputStream(this.soquete.getInputStream());
	}

	public void enviar_mensagem(String mensagem) throws Exception {
		this.saida.writeObject(mensagem);
	}
	
	public Object receber_mensagem() throws Exception {
		return this.entrada.readObject();
	}
	
	public void finalizar() throws IOException {
		this.soquete.close();
	}
	
	public static void main(String[] args) throws Exception {
		Cliente cliente = new Cliente("127.0.0.1", 15500);
        
        String nome = "Junnin";
        String idade = "27";
        String cpf = "130.404.656-78";
        String msg = "cachorro";

        System.out.println("-------------------------------------------------CLIENTE----------------------------------------------------------");

        String json = json(nome, idade, cpf, msg);
		cliente.enviar_mensagem(json);

        String xml = xml(nome, idade, cpf, msg);
		cliente.enviar_mensagem(xml);

        String yaml = yaml(nome, idade, cpf, msg);
        cliente.enviar_mensagem(yaml);

        String toml = toml(nome, idade, cpf, msg);
        cliente.enviar_mensagem(toml);

        String csv = csv(nome, idade, cpf, msg);
        cliente.enviar_mensagem(csv);

		cliente.finalizar();
	}

    public static String json(String nome, String idade, String cpf, String msg) {
        Gson gson = new Gson();

        Mensagem mensagemJson = new Mensagem(nome, idade, cpf, msg);

        String json = gson.toJson(mensagemJson);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("JSON: " + json);

        return json;
    }

    public static String xml(String nome, String idade, String cpf, String msg) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();

        Mensagem mensagemXml = new Mensagem(nome, idade, cpf, msg);

        String xml = xmlMapper.writeValueAsString(mensagemXml);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("XML: " + xml);

        return xml;
    }

    public static String yaml(String nome, String idade, String cpf, String msg) {
        Yaml yaml = new Yaml();

        Mensagem mensagemYaml = new Mensagem(nome, idade, cpf, msg);

        String yamlString = yaml.dump(mensagemYaml);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("YAML: " + yamlString);
        
        return yamlString;
    }

    public static String toml(String nome, String idade, String cpf, String msg) {
        TomlWriter tomlWriter = new TomlWriter();

        Mensagem mensagemToml = new Mensagem(nome, idade, cpf, msg);

        String tomlString = tomlWriter.write(mensagemToml);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("TOML: " + tomlString);

        return tomlString;
    }

    public static String csv(String nome, String idade, String cpf, String msg) throws IOException {
        CsvMapper csvMapper = new CsvMapper();

        Mensagem mensagemCsv = new Mensagem(nome, idade, cpf, msg);

        CsvSchema schema = csvMapper.schemaFor(Mensagem.class);
        
        String csv = csvMapper.writer(schema).writeValueAsString(mensagemCsv);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("CSV: " + csv);
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        return csv;
    }
}