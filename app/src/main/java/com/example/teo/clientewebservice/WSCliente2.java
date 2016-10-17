package com.example.teo.clientewebservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;


public class WSCliente2 {

    //deixa os erros para serem tratados na classe que fizer uso do método
    public String getVersion() throws IOException, XmlPullParserException {
        //name space + nome do método
        SoapObject soap = new SoapObject("http://axisversion.sample","getVersion");
        //soap.addProperty("in0",cod); //passagem dos parâmetros
        //soap.addProperty("in1",data);  //passagem dos parâmetros
        //cria um envelope para enviar e receber a requisição SOAP no parâmetro define-se a versão
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap); //envia o objeto soap para nosso webservice
        //Este objeto será responsável pelo transporte do nosso envelope, passando como parâmetro no wsdl
        HttpTransportSE httpT = new HttpTransportSE("http://177.91.185.2:8080/axis2/services/Version?wsdl");
        httpT.call("getVersion",envelope); //envia o objeto, passa como parâmetros o nome do método mais o envelope

        Object resultado = envelope.getResponse(); //pega a resposta do webService

        return resultado.toString(); //devolpe para quem solicitou o método
    }
}






