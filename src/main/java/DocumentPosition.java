import java.math.BigDecimal;

public class DocumentPosition {
  private int lp;
  private String name;
  private String code;
  private String unit;
  private int quantity;
  private BigDecimal purchasePrice;
  private BigDecimal totalValue;

  public int getLp() {
    return lp;
  }

  public void setLp(int lp) {
    this.lp = lp;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(BigDecimal purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public BigDecimal getTotalValue() {
    return totalValue;
  }

  public void setTotalValue(BigDecimal totalValue) {
    this.totalValue = totalValue;
  }

  @Override
  public String toString() {
    return "DocumentPosition{" +
        "lp=" + lp +
        ", name='" + name + '\'' +
        ", code='" + code + '\'' +
        ", unit='" + unit + '\'' +
        ", quantity=" + quantity +
        ", purchasePrice=" + purchasePrice +
        ", totalValue=" + totalValue +
        '}';
  }
}
