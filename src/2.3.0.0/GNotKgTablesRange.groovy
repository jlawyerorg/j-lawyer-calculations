class GNotKgTablesRange {
    float low
    float high
    float mappedValue

    GNotKgTablesRange(low, high, mappedValue) {
        this.low=low
        this.high=high
        this.mappedValue=mappedValue
    }

    boolean contains(float number){(number > low && number <= high)}

    boolean contains(double number){(number > low && number <= high)}
}
