package labshoppubsub.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import labshoppubsub.DeliveryApplication;
import lombok.Data;

@Entity
@Table(name = "Delivery_table")
@Data
//<<< DDD / Aggregate Root
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderId;

    private String customerId;

    private String address;

    public static DeliveryRepository repository() {
        DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext.getBean(
            DeliveryRepository.class
        );
        return deliveryRepository;
    }

    //<<< Clean Arch / Port Method
    public static void addDelivery(OrderPlaced orderPlaced) {
        //implement business logic here:


        Delivery delivery = new Delivery();
        delivery.setOrderId(orderPlaced.getId());
        delivery.setAddress(orderPlaced.getAddress());
        delivery.setCustomerId(orderPlaced.getCustomerId());
        repository().save(delivery);

        DeliveryAdded deliveryAdded = new DeliveryAdded(delivery);
        deliveryAdded.publishAfterCommit();
   

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
