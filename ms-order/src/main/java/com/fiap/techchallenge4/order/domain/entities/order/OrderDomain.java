package com.fiap.techchallenge4.order.domain.entities.order;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;
import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static com.fiap.techchallenge4.order.utils.FormatterUtils.formatCpf;
import static com.fiap.techchallenge4.order.utils.FormatterUtils.formatRealBrasileiro;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

public class OrderDomain implements Order {
    private Long id;
    private String cpf;
    private OrderStatusEnum status;
    private LocalDateTime creationDate;
    private LocalDateTime completionDate;
    private List<Product> products;
    private Shipping shipping;
    private BigDecimal total;

    public static Order of(
            final Long id,
            final String cpf,
            final String status,
            final BigDecimal total,
            final LocalDateTime creationDate,
            final LocalDateTime completionDate,
            final List<Product> products,
            final Shipping shipping
    ) {
        return new OrderDomain(
                id,
                cpf,
                status,
                total,
                creationDate,
                completionDate,
                products,
                shipping
        );
    }

    public static Order of(
            final String cpf,
            final String status,
            final LocalDateTime creationDate,
            final List<Product> products
    ) {
        return new OrderDomain(
                cpf,
                status,
                creationDate,
                products
        );
    }

    private OrderDomain(
            final String cpf,
            final String status,
            final LocalDateTime creationDate,
            final List<Product> products
    ) {
        this.cpf = cpfValidation(cpf);
        this.products = productsValidation(products);
        this.status = statusValidation(status);
        this.creationDate = creationDateValidation(creationDate);

        final var calculatedTotal = calculateTotal(products);
        this.total = totalValidation(calculatedTotal);
    }

    private OrderDomain(
            final Long id,
            final String cpf,
            final String status,
            final BigDecimal total,
            final LocalDateTime creationDate,
            final LocalDateTime completionDate,
            final List<Product> products,
            final Shipping shipping
    ) {
        this.id = requireNonNull(id, "Order Id cannot be null");
        this.cpf = cpfValidation(cpf);
        this.products = productsValidation(products);
        this.status = statusValidation(status);
        this.creationDate = creationDateValidation(creationDate);

        if (isNull(total) || total.signum() < 1) throw CustomValidationException.of("Order Total", "is invalid");
        this.total = total;

        this.shipping = requireNonNull(shipping);

        if (this.status.equals(OrderStatusEnum.FINALIZADO)) {
            if (isNull(completionDate)) throw CustomValidationException.of("Order Completion Date", "cannot be null");
            if (creationDate.isAfter(completionDate))
                throw CustomValidationException.of("Order Completion Date", "is before to creation date");
            this.completionDate = completionDate;
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public String getMaskedCpf() {
        return formatCpf(cpf);
    }

    @Override
    public String getStatus() {
        return status.toString();
    }

    @Override
    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Shipping getShipping() {
        return shipping;
    }

    @Override
    public String getFormattedTotal() {
        return formatRealBrasileiro(total);
    }

    @Override
    public String getFormattedTotalWithShipping() {
        return formatRealBrasileiro(getTotalWithShipping());
    }

    @Override
    public BigDecimal getTotalWithShipping() {
        return nonNull(shipping)
                ? total.add(shipping.getPrice()).setScale(2, RoundingMode.HALF_UP)
                : total;
    }

    @Override
    public void updateShippingInfo(final Shipping shipping) {
        this.shipping = shipping;
    }

    @Override
    public void updateToPendingPayment() {
        status = OrderStatusEnum.PENDENTE_PAGAMENTO;
    }

    @Override
    public void updateToProcessed() {
        status = OrderStatusEnum.PROCESSADO;
    }

    @Override
    public void updateToWaitShipping() {
        status = OrderStatusEnum.AGUARDANDO_ENVIO;
    }

    @Override
    public void updateToCanceled() {
        status = OrderStatusEnum.CANCELADO;
    }

    private static String cpfValidation(final String cpf) {
        if (isNull(cpf)) throw CustomValidationException.of("Customer CPF", "cannot be null");
        if (cpf.length() < 11) throw CustomValidationException.of("Customer CPF", "must be 11 positions");
        return cpf;
    }

    private static BigDecimal totalValidation(final BigDecimal total) {
        if (isNull(total)) throw CustomValidationException.of("Order Total", "cannot be null");
        if (total.signum() < 1) throw CustomValidationException.of("Order Total", "cannot be negative or zero");
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private static List<Product> productsValidation(final List<Product> products) {
        if (isEmpty(products)) throw CustomValidationException.of("Order Products", "not exists");
        return products;
    }

    private static OrderStatusEnum statusValidation(final String status) {
        if (isNull(status)) throw CustomValidationException.of("Order Status", "cannot be null");
        final var orderStatusEnum = OrderStatusEnum.of(status);
        return requireNonNull(orderStatusEnum, "Status is invalid");
    }

    private LocalDateTime creationDateValidation(final LocalDateTime creationDate) {
        if (isNull(creationDate)) throw CustomValidationException.of("Order Created Date", "cannot be null");
        return creationDate;
    }

    private static BigDecimal calculateTotal(final List<Product> products) {
        return products.stream()
                .map(Product::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
