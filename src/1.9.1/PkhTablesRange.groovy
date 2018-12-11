class PkhTablesRange { 
    float low
    float high
    float mappedValue
    
    PkhTablesRange(low, high, mappedValue) {
        this.low=low
        this.high=high
        this.mappedValue=mappedValue
    }
    
    boolean contains(float number){(number > low && number <= high)}
}
