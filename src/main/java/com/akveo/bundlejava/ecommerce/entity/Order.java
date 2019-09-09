package com.akveo.bundlejava.ecommerce.entity;

import com.akveo.bundlejava.ecommerce.entity.enums.OrderStatusEnum;
import com.akveo.bundlejava.ecommerce.entity.enums.OrderTypeEnum;
import com.akveo.bundlejava.ecommerce.entity.enums.converter.OrderStatusConverter;
import com.akveo.bundlejava.ecommerce.entity.enums.converter.OrderTypeConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "Order")
@Table(name = "orders")
public class Order extends TrackableEntity{
    public Order() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "value", precision = 6, scale = 2)
    private BigDecimal value;

    @Column(name = "currency")
    private String currency;

    @Convert(converter = OrderTypeConverter.class)
    private OrderTypeEnum type;

    @Convert(converter = OrderStatusConverter.class)
    private OrderStatusEnum status;

    @ManyToOne (optional = false)
    @JoinColumn (name = "country_id")
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OrderTypeEnum getType() {
        return type;
    }

    public void setType(OrderTypeEnum type) {
        this.type = type;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(name, order.name) &&
                Objects.equals(date, order.date) &&
                Objects.equals(value, order.value) &&
                Objects.equals(currency, order.currency) &&
                type == order.type &&
                status == order.status &&
                Objects.equals(country, order.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, value, currency, type, status, country);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", value=" + value +
                ", currency='" + currency + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", country=" + country +
                '}';
    }
}
