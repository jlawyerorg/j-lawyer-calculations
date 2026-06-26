class GNotKgTablesRange {
    BigDecimal low
    BigDecimal high
    BigDecimal mappedValue

    GNotKgTablesRange(low, high, mappedValue) {
        this.low=low as BigDecimal
        this.high=high as BigDecimal
        this.mappedValue=mappedValue as BigDecimal
    }

    boolean contains(Number number){ BigDecimal n=number as BigDecimal; (n > low && n <= high) }
}
