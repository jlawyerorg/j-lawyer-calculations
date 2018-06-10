class RvgTablesRange { 
    float low
    float high
    float mappedValue
    
    RvgTablesRange(low, high, mappedValue) {
        this.low=low
        this.high=high
        this.mappedValue=mappedValue
    }
    
    boolean contains(float number){(number > low && number <= high)}
}
