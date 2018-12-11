class pkhtables_range { 
    float low
    float high
    float mappedValue
    
    pkhtables_range(low, high, mappedValue) {
        this.low=low
        this.high=high
        this.mappedValue=mappedValue
    }
    
    boolean contains(float number){(number > low && number <= high)}
}
