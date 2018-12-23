class GkgTablesRange { 
    float low
    float high
    float mappedValue
    
    GkgTablesRange(low, high, mappedValue) {
        this.low=low
        this.high=high
        this.mappedValue=mappedValue
    }
    
    boolean contains(float number){(number > low && number <= high)}
}
