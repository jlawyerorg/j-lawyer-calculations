import java.util.ArrayList
import PkhTablesRange

class PkhTablesRangeList { 
    ArrayList<PkhTablesRange> ranges
    
    PkhTablesRangeList() {
        ranges=new ArrayList<PkhTablesRange>()
        ranges.add(new PkhTablesRange(0,500,45))
        ranges.add(new PkhTablesRange(500,1000,80))
        ranges.add(new PkhTablesRange(1000,1500,115))
        ranges.add(new PkhTablesRange(1500,2000,150))
        ranges.add(new PkhTablesRange(2000,3000,201))
        ranges.add(new PkhTablesRange(3000,4000,252))
        ranges.add(new PkhTablesRange(4000,5000,257))
        ranges.add(new PkhTablesRange(5000,6000,267))
        ranges.add(new PkhTablesRange(6000,7000,277))
        ranges.add(new PkhTablesRange(7000,8000,287))
        ranges.add(new PkhTablesRange(8000,9000,297))
        ranges.add(new PkhTablesRange(9000,10000,307))
        ranges.add(new PkhTablesRange(10000,13000,321))
        ranges.add(new PkhTablesRange(13000,16000,335))
        ranges.add(new PkhTablesRange(16000,19000,349))
        ranges.add(new PkhTablesRange(19000,22000,363))
        ranges.add(new PkhTablesRange(22000,25000,377))
        ranges.add(new PkhTablesRange(25000,30000,412))
        ranges.add(new PkhTablesRange(30000,500000,447))
    }
    
    ArrayList<PkhTablesRange> getRanges() {
        return ranges
    }
    
    float getMappedValue(float streitWert) {
           for(PkhTablesRange r: ranges) {
       if(r.contains(streitWert))
            return r.mappedValue
   }
   return -1f
    }
    
}



