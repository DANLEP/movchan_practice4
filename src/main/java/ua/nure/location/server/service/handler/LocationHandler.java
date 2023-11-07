package ua.nure.location.server.service.handler;

import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.Set;

public class LocationHandler implements SOAPHandler<SOAPMessageContext> {
    private final Logger log = LoggerFactory.getLogger(LocationHandler.class);

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        SOAPMessage msg = context.getMessage();
        boolean isOutbound = (boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
        boolean result = true;
        try {
            if (isOutbound) {
                result = handleOutboundMessge(msg);
            } else {
                result = handleInboundMessge(msg);
            }
        } catch (SOAPException e) {
            log.error("", e);
            result = generateSOAPErrMessage(msg, "Your reason");
        }
        return result;
    }

    private boolean handleInboundMessge(SOAPMessage msg) throws SOAPException {
        log.info("LocationHandler: Inbound message");
        return true;
    }

    private boolean handleOutboundMessge(SOAPMessage msg) throws SOAPException {
        log.info("LocationHandler: Outbound message");
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        log.info("LocationHandler: handleFault()");
        return true;
    }

    @Override
    public void close(MessageContext context) {
        log.info("LocationHandler: close()");
    }

    @Override
    public Set<QName> getHeaders() {
        log.info("getHeaders()");
        return Set.of();
    }


    private boolean generateSOAPErrMessage(SOAPMessage msg, String reason) {
        try {
            SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
            SOAPFault soapFault = soapBody.addFault();
            soapFault.setFaultString(reason);
            soapFault.setFaultCode(reason);
            throw new SOAPFaultException(soapFault);
        } catch (SOAPException e) {
            // nothing
        }
        return false;
    }

}
