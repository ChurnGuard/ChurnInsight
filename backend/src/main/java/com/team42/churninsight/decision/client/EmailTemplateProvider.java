package com.team42.churninsight.decision.client;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailTemplateProvider {

    Map<String, EmailContent> templates = new HashMap<>();

    public EmailTemplateProvider() {

        templates.put("HIGH_RISK_LOW_VALUE_INACTIVITY_RISK", new EmailContent(
                "¡Te extrañamos! Tenemos un regalo para tu regreso",
                """
                        Hola, hace tiempo que no nos visitas y queremos que vuelvas.
                        Aprovecha un 15% de descuento en tu próxima compra usando el código: VOLVER15. ¡Te esperamos!
                        """
        ));
        templates.put("HIGH_RISK_LOW_VALUE_FINANCIAL_RISK", new EmailContent(
                "Descubre lo nuevo: Calidad al mejor precio",
                """
                        Hola, queremos que sigas disfrutando de lo mejor sin gastar de más. 
                        Mira nuestra selección de productos esenciales con precios increíbles. 
                        Además, ¡invita a un amigo y ambos ganan crédito en su cuenta!
                        """
        ));
        templates.put("HIGH_RISK_LOW_VALUE_PROMO_ABUSE", new EmailContent(
                "Descubre todo lo que puedes hacer con nosotros",
                """
                        Hola, ¿sabías que nuestro servicio incluye beneficios exclusivos más allá de las ofertas?
                        Te invitamos a conocer cómo sacar el máximo provecho a tu cuenta.
                        Como agradecimiento, tienes un cupón único del 10% válido por 48 horas.
                        """
        ));
        templates.put("HIGH_RISK_MEDIUM_VALUE_INACTIVITY_RISK", new EmailContent(
                "Tu cuenta tiene beneficios esperando ser usados",
                """
                        Hola, valoramos mucho tu preferencia. 
                        Notamos tu ausencia y queremos recordarte que tienes acceso a envíos prioritarios y soporte exclusivo. 
                        Para tu regreso, hemos activado un cupón especial de $10 USD en tu carrito.
                        """
        ));
        templates.put("HIGH_RISK_MEDIUM_VALUE_FINANCIAL_RISK", new EmailContent(
                "Tus compras favoritas, ahora en cómodas cuotas",
                """
                        Hola, queremos facilitarte el acceso a lo que te gusta. 
                        Ahora puedes financiar tus compras con nuestro programa de cuotas sin interés en productos seleccionados. 
                        ¡Haz tu pedido hoy y paga poco a poco!
                        """
        ));
        templates.put("HIGH_RISK_MEDIUM_VALUE_PROMO_ABUSE", new EmailContent(
                "¡Eres parte de nuestro programa de puntos exclusivos!",
                """
                        Hola, queremos premiar tu fidelidad de una forma diferente. 
                        Ahora cada interacción cuenta: 
                        acumula puntos por cada compra y canjéalos por experiencias, lanzamientos anticipados y beneficios VIP. 
                        ¡Sigue sumando!
                        """
        ));
        templates.put("ESSENTIAL_MODERATE_BUYER_NO_RISK", new EmailContent(
                "Descubre lo nuevo: Calidad al mejor precio",
                """
                        Hola, queremos que sigas disfrutando de lo mejor sin gastar de más. 
                        Mira nuestra selección de productos esenciales con precios increíbles. 
                        Además, ¡invita a un amigo y ambos ganan crédito en su cuenta!
                        """
        ));
        templates.put("LOW_RISK_HIGH_VALUE_NO_FLAG", new EmailContent(
                "¡Eres parte de nuestro programa de puntos exclusivos!",
                """
                        Hola, queremos premiar tu fidelidad de una forma diferente. 
                        Ahora cada interacción cuenta: 
                        acumula puntos por cada compra y canjéalos por experiencias, lanzamientos anticipados y beneficios VIP. 
                        ¡Sigue sumando!
                        """
        ));
        templates.put("LOW_RISK_HIGH_VALUE_ANY_FLAG", new EmailContent(
                "¡Eres parte de nuestro programa de puntos exclusivos!",
                """
                        Hola, queremos premiar tu fidelidad de una forma diferente. 
                        Ahora cada interacción cuenta: 
                        acumula puntos por cada compra y canjéalos por experiencias, lanzamientos anticipados y beneficios VIP. 
                        ¡Sigue sumando!
                        """
        ));
    }

    public EmailContent getTemplate(String action) {
        return templates.get(action);
    }
}
