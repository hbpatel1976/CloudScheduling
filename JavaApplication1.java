import java.io.File;  
import java.io.FileInputStream;  
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class JavaApplication1
    {  
    static void firstFit(int memoryRequirement[], int timeForMemoryRequirement[], int computationRequirement[], int bandwidthRequirement[], int timeForBandwidthRequirement[], int m, int vmMemory[], int vmComputation[], int vmBandwidth[], int n)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now; 
        int i, j, totalAllocation=0, vmVacated=0, tempi, tempj, deliberateDelay=500;
        long start, finish, timeElapsed;
        int isVMAllotted[] = new int[n]; 
        int isProcessAllotted[] = new int[m];
        for (i = 0; i < n; i++)isVMAllotted[i] = -1; // Initially, all VMs are empty. Nothing has been assigned to any of the VM
        for (j = 0; j < m; j++)isProcessAllotted[j] = 0; // No process has been assigned to any of the VM
        start = System.currentTimeMillis();
        do
        {
            if(vmVacated>=(n-1)) // If all VMs are empty, restart the counter of reassigning the processes to VMs
            {
                start = System.currentTimeMillis();
                vmVacated=0;
            }
            for (j = 0; j < m; j++) // Process/Job/Task
            { 
                for (i = 0; i < n; i++) // VM
                { 
                    if ((isProcessAllotted[j] != 1) && (isVMAllotted[i]==-1) && (vmMemory[i] >= memoryRequirement[j] && vmComputation[i] >= computationRequirement[j] && vmBandwidth[i] >= bandwidthRequirement[j]))
                    { 
                        tempi = i+1;
                        tempj = j+1;
                        isVMAllotted[i] = j; 
                        isProcessAllotted[j] = 1;
                        totalAllocation++;
                        now = LocalDateTime.now();  
                        System.out.println("VM # ,"+ tempi +", is allotted to Process # ,"+ tempj + ", at time ," + dtf.format(now));
                        //try {Thread.sleep(50);} catch (InterruptedException e) { Thread.currentThread().interrupt();}
                        break; 
                    } 
                } 
            } 
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            //System.out.println("Execution TIme : " + timeElapsed + " ms");
            for (i = 0; i < n; i++)
            {
                if ((isVMAllotted[i]!=-1) && ((timeForMemoryRequirement[isVMAllotted[i]]*deliberateDelay)<=timeElapsed))
                {
                    vmVacated ++;
                    tempi = i+1;
                    tempj = isVMAllotted[i]+1;
                    now = LocalDateTime.now();
                    System.out.println("VM # ," + tempi + ", vacated process # ," +  tempj + ", at time ," + dtf.format(now));
                    isVMAllotted[i] = -1;
                } 
            }
        }while(totalAllocation < m);
        
        // Vacate rest of the VMs which could not have been vacated earlier
        for (i = 0; i < n; i++) 
        { 
            if (isVMAllotted[i] != -1) 
            {
                tempi = i+1;
                tempj = isVMAllotted[i]+1;
                now = LocalDateTime.now();
                System.out.println("VM # ," + tempi + ", vacated process # ," +  tempj + ", at time ," + dtf.format(now));
            } 
        } 
    } 
    
    public static void main(String[] args)   
        {
        int i, m, n;
        int vm20Memory [] = new int[21]; int vm20Computation [] = new int[21]; int vm20Bandwidth [] = new int[21];
        int vm50Memory [] = new int[51];int vm50Computation [] = new int[51];int vm50Bandwidth [] = new int[51];
        int vm100Memory [] = new int[101];int vm100Computation [] = new int[101];int vm100Bandwidth [] = new int[101];
        
        int process1001memoryRequirement [] = new int[101];int process1001timeForMemoryRequirement [] = new int[101];int process1001computationRequirement [] = new int[101];int process1001bandwidthRequirement [] = new int[101];int process1001timeForBandwidthRequirement [] = new int[101];
        int process1002memoryRequirement [] = new int[101];int process1002timeForMemoryRequirement [] = new int[101];int process1002computationRequirement [] = new int[101];int process1002bandwidthRequirement [] = new int[101];int process1002timeForBandwidthRequirement [] = new int[101];
        int process200memoryRequirement [] = new int[201];int process200timeForMemoryRequirement [] = new int[201];int process200computationRequirement [] = new int[201];int process200bandwidthRequirement [] = new int[201];int process200timeForBandwidthRequirement [] = new int[201];
        int process500memoryRequirement [] = new int[501];int process500timeForMemoryRequirement [] = new int[501];int process500computationRequirement [] = new int[501];int process500bandwidthRequirement [] = new int[501];int process500timeForBandwidthRequirement [] = new int[501];
        File filePointer;
        FileInputStream fileHandler;
        try  
            {  
            filePointer = new File("C:\\Users\\VSITR\\Documents\\NetBeansProjects\\Jalpa\\src\\main\\java\\datasetVM.xlsx");
            fileHandler = new FileInputStream(filePointer);
            XSSFWorkbook workbook = new XSSFWorkbook(fileHandler);
            XSSFSheet vm20Worksheet = workbook.getSheetAt(0);
            Iterator<Row> vm20iteration = vm20Worksheet.iterator();
            i=0;
            while (vm20iteration.hasNext())                 
                {  
                Row rowIterator = vm20iteration.next();  
                Iterator<Cell> cellIterator = rowIterator.cellIterator();
                Cell cell;
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING:i = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC:    i = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }  
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: vm20Memory[i] = Integer.parseInt(cell.getStringCellValue()); break;  
                    case NUMERIC: vm20Memory[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: vm20Computation[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: vm20Computation[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING:vm20Bandwidth[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC:    vm20Bandwidth[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                System.out.println(i + "\t" + vm20Memory[i] + "\t" + vm20Computation[i] + "\t" + vm20Bandwidth[i]);
                }  
            }catch(Exception e){e.printStackTrace();}  
            System.out.println("**");
            try  
            {  
            filePointer = new File("C:\\Users\\VSITR\\Documents\\NetBeansProjects\\Jalpa\\src\\main\\java\\datasetVM.xlsx");
            fileHandler = new FileInputStream(filePointer);
            XSSFWorkbook workbook = new XSSFWorkbook(fileHandler);
            XSSFSheet vm50Worksheet = workbook.getSheetAt(1);
            Iterator<Row> vm50iteration = vm50Worksheet.iterator();
            i=0;
            while (vm50iteration.hasNext())                 
                {  
                Row rowIterator = vm50iteration.next();  
                Iterator<Cell> cellIterator = rowIterator.cellIterator();
                Cell cell;
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING:i = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC:    i = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }  
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: vm50Memory[i] = Integer.parseInt(cell.getStringCellValue()); break;  
                    case NUMERIC: vm50Memory[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: vm50Computation[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: vm50Computation[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING:vm50Bandwidth[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC:    vm50Bandwidth[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                System.out.println(i + "\t" + vm50Memory[i] + "\t" + vm50Computation[i] + "\t" + vm50Bandwidth[i]);
                }  
            }catch(Exception e){e.printStackTrace();}  
        System.out.println("**");
            try  
            {  
            filePointer = new File("C:\\Users\\VSITR\\Documents\\NetBeansProjects\\Jalpa\\src\\main\\java\\datasetVM.xlsx");
            fileHandler = new FileInputStream(filePointer);
            XSSFWorkbook workbook = new XSSFWorkbook(fileHandler);
            XSSFSheet vm100Worksheet = workbook.getSheetAt(2);
            Iterator<Row> vm100iteration = vm100Worksheet.iterator();
            i=0;
            while (vm100iteration.hasNext())                 
                {  
                Row rowIterator = vm100iteration.next();  
                Iterator<Cell> cellIterator = rowIterator.cellIterator();
                Cell cell;
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING:i = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC:    i = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }  
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: vm100Memory[i] = Integer.parseInt(cell.getStringCellValue()); break;  
                    case NUMERIC: vm100Memory[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: vm100Computation[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: vm100Computation[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING:vm100Bandwidth[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC:    vm100Bandwidth[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                System.out.println(i + "\t" + vm100Memory[i] + "\t" + vm100Computation[i] + "\t" + vm100Bandwidth[i]);
                }  
            }catch(Exception e){e.printStackTrace();}      
       
        System.out.println("**");    
        try  
            {  
            filePointer = new File("C:\\Users\\VSITR\\Documents\\NetBeansProjects\\Jalpa\\src\\main\\java\\datasetProcess.xlsx");
            fileHandler = new FileInputStream(filePointer);
            XSSFWorkbook workbook = new XSSFWorkbook(fileHandler);
            XSSFSheet process1001Worksheet = workbook.getSheetAt(0);
            Iterator<Row> process1001iteration = process1001Worksheet.iterator();
            i=0;
            while (process1001iteration.hasNext())                 
                {  
                Row rowIterator = process1001iteration.next();  
                Iterator<Cell> cellIterator = rowIterator.cellIterator();
                Cell cell;
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING:i = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC:    i = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }  
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process1001memoryRequirement[i] = Integer.parseInt(cell.getStringCellValue()); break;  
                    case NUMERIC: process1001memoryRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process1001timeForMemoryRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process1001timeForMemoryRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process1001computationRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process1001computationRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process1001bandwidthRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process1001bandwidthRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process1001timeForBandwidthRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process1001timeForBandwidthRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                System.out.println(i + "\t" + process1001memoryRequirement[i] + "\t" + process1001timeForMemoryRequirement[i] + "\t" + process1001computationRequirement[i] + "\t" +  process1001bandwidthRequirement[i] + "\t" + process1001timeForBandwidthRequirement[i]);
                }  
            }catch(Exception e){e.printStackTrace();}  
            System.out.println("**");    
            
        try  
            {  
            filePointer = new File("C:\\Users\\VSITR\\Documents\\NetBeansProjects\\Jalpa\\src\\main\\java\\datasetProcess.xlsx");
            fileHandler = new FileInputStream(filePointer);
            XSSFWorkbook workbook = new XSSFWorkbook(fileHandler);
            XSSFSheet process1002Worksheet = workbook.getSheetAt(1);
            Iterator<Row> process1002iteration = process1002Worksheet.iterator();
            i=0;
            while (process1002iteration.hasNext())                 
                {  
                Row rowIterator = process1002iteration.next();  
                Iterator<Cell> cellIterator = rowIterator.cellIterator();
                Cell cell;
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING:i = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC:    i = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }  
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process1002memoryRequirement[i] = Integer.parseInt(cell.getStringCellValue()); break;  
                    case NUMERIC: process1002memoryRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process1002timeForMemoryRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process1002timeForMemoryRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process1002computationRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process1002computationRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process1002bandwidthRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process1002bandwidthRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process1002timeForBandwidthRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process1002timeForBandwidthRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                System.out.println(i + "\t" + process1002memoryRequirement[i] + "\t" + process1002timeForMemoryRequirement[i] + "\t" + process1002computationRequirement[i] + "\t" +  process1002bandwidthRequirement[i] + "\t" + process1002timeForBandwidthRequirement[i]);
                }  
            }catch(Exception e){e.printStackTrace();}  
       System.out.println("**");    
       
       try  
            {  
            filePointer = new File("C:\\Users\\VSITR\\Documents\\NetBeansProjects\\Jalpa\\src\\main\\java\\datasetProcess.xlsx");
            fileHandler = new FileInputStream(filePointer);
            XSSFWorkbook workbook = new XSSFWorkbook(fileHandler);
            XSSFSheet process200Worksheet = workbook.getSheetAt(2);
            Iterator<Row> process200iteration = process200Worksheet.iterator();
            i=0;
            while (process200iteration.hasNext())                 
                {  
                Row rowIterator = process200iteration.next();  
                Iterator<Cell> cellIterator = rowIterator.cellIterator();
                Cell cell;
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING:i = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC:    i = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }  
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process200memoryRequirement[i] = Integer.parseInt(cell.getStringCellValue()); break;  
                    case NUMERIC: process200memoryRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process200timeForMemoryRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process200timeForMemoryRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process200computationRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process200computationRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process200bandwidthRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process200bandwidthRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process200timeForBandwidthRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process200timeForBandwidthRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                System.out.println(i + "\t" + process200memoryRequirement[i] + "\t" + process200timeForMemoryRequirement[i] + "\t" + process200computationRequirement[i] + "\t" +  process200bandwidthRequirement[i] + "\t" + process200timeForBandwidthRequirement[i]);
                }  
            }catch(Exception e){e.printStackTrace();}  
       System.out.println("**");
       
       try  
            {  
            filePointer = new File("C:\\Users\\VSITR\\Documents\\NetBeansProjects\\Jalpa\\src\\main\\java\\datasetProcess.xlsx");
            fileHandler = new FileInputStream(filePointer);
            XSSFWorkbook workbook = new XSSFWorkbook(fileHandler);
            XSSFSheet process500Worksheet = workbook.getSheetAt(3);
            Iterator<Row> process500iteration = process500Worksheet.iterator();
            i=0;
            while (process500iteration.hasNext())                 
                {  
                Row rowIterator = process500iteration.next();  
                Iterator<Cell> cellIterator = rowIterator.cellIterator();
                Cell cell;
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING:i = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC:    i = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }  
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process500memoryRequirement[i] = Integer.parseInt(cell.getStringCellValue()); break;  
                    case NUMERIC: process500memoryRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process500timeForMemoryRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process500timeForMemoryRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process500computationRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process500computationRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process500bandwidthRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process500bandwidthRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                cell = cellIterator.next();
                switch (cell.getCellTypeEnum())               
                    {  
                    case STRING: process500timeForBandwidthRequirement[i] = Integer.parseInt(cell.getStringCellValue());break;  
                    case NUMERIC: process500timeForBandwidthRequirement[i] = (int) cell.getNumericCellValue();  break;  
                    default:  
                    }
                System.out.println(i + "\t" + process500memoryRequirement[i] + "\t" + process500timeForMemoryRequirement[i] + "\t" + process500computationRequirement[i] + "\t" +  process500bandwidthRequirement[i] + "\t" + process500timeForBandwidthRequirement[i]);
                }  
            }catch(Exception e){e.printStackTrace();}  
       System.out.println("**");

       // *********************************************
        System.out.println("Combination 1 : VM = 20 Processes = 100 (Set1) .... Starts");
        m = 100; 
        n = 20; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process1001memoryRequirement, process1001timeForMemoryRequirement, process1001computationRequirement, process1001bandwidthRequirement, process1001timeForBandwidthRequirement, m, vm20Memory, vm20Computation, vm20Bandwidth, n); 
        System.out.println("Combination 1 : VM = 20 Processes = 100 (Set1) .... Ends");

        // *********************************************
        System.out.println("Combination 2 : VM = 20 Processes = 100 (Set2) .... Starts");
        m = 100; 
        n = 20; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process1002memoryRequirement, process1002timeForMemoryRequirement, process1002computationRequirement, process1002bandwidthRequirement, process1002timeForBandwidthRequirement, m, vm20Memory, vm20Computation, vm20Bandwidth, n); 
        System.out.println("Combination 2 : VM = 20 Processes = 100 (Set2) .... Ends");

        // *********************************************
        System.out.println("Combination 3 : VM = 20 Processes = 200 .... Starts");
        m = 200; 
        n = 20; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process200memoryRequirement, process200timeForMemoryRequirement, process200computationRequirement, process200bandwidthRequirement, process200timeForBandwidthRequirement, m, vm20Memory, vm20Computation, vm20Bandwidth, n); 
        System.out.println("Combination 3 : VM = 20 Processes = 200 .... Ends");

        // *********************************************
        System.out.println("Combination 4 : VM = 20 Processes = 500 .... Starts");
        m = 500; 
        n = 20; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process500memoryRequirement, process500timeForMemoryRequirement, process500computationRequirement, process500bandwidthRequirement, process500timeForBandwidthRequirement, m, vm20Memory, vm20Computation, vm20Bandwidth, n); 
        System.out.println("Combination 4 : VM = 20 Processes = 500 .... Ends");

        // *********************************************
        System.out.println("Combination 5 : VM = 50 Processes = 100 (Set1) .... Starts");
        m = 100; 
        n = 50; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process1001memoryRequirement, process1001timeForMemoryRequirement, process1001computationRequirement, process1001bandwidthRequirement, process1001timeForBandwidthRequirement, m, vm50Memory, vm50Computation, vm50Bandwidth, n); 
        System.out.println("Combination 5 : VM = 50 Processes = 100 (Set1) .... Ends");

        // *********************************************
        System.out.println("Combination 6 : VM = 50 Processes = 100 (Set2) .... Starts");
        m = 100; 
        n = 50; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process1002memoryRequirement, process1002timeForMemoryRequirement, process1002computationRequirement, process1002bandwidthRequirement, process1002timeForBandwidthRequirement, m, vm50Memory, vm50Computation, vm50Bandwidth, n); 
        System.out.println("Combination 6 : VM = 50 Processes = 100 (Set2) .... Ends");
        
        // *********************************************
        System.out.println("Combination 7 : VM = 50 Processes = 200 .... Starts");
        m = 200; 
        n = 50; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process200memoryRequirement, process200timeForMemoryRequirement, process200computationRequirement, process200bandwidthRequirement, process200timeForBandwidthRequirement, m, vm50Memory, vm50Computation, vm50Bandwidth, n); 
        System.out.println("Combination 7 : VM = 50 Processes = 200 .... Ends");
        
        // *********************************************
        System.out.println("Combination 8 : VM = 50 Processes = 500 .... Starts");
        m = 500; 
        n = 50; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process500memoryRequirement, process500timeForMemoryRequirement, process500computationRequirement, process500bandwidthRequirement, process500timeForBandwidthRequirement, m, vm50Memory, vm50Computation, vm50Bandwidth, n); 
        System.out.println("Combination 8 : VM = 50 Processes = 500 .... Ends");
        
         // *********************************************
        System.out.println("Combination 9 : VM = 100 Processes = 100 (Set 1) .... Starts");
        m = 100; 
        n = 100; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process1001memoryRequirement, process1001timeForMemoryRequirement, process1001computationRequirement, process1001bandwidthRequirement, process1001timeForBandwidthRequirement, m, vm100Memory, vm100Computation, vm100Bandwidth, n); 
        System.out.println("Combination 9 : VM = 100 Processes = 100 (Set 1) .... Ends");

         // *********************************************
        System.out.println("Combination 10 : VM = 100 Processes = 100 (Set 2) .... Starts");
        m = 100; 
        n = 100; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process1002memoryRequirement, process1002timeForMemoryRequirement, process1002computationRequirement, process1002bandwidthRequirement, process1002timeForBandwidthRequirement, m, vm100Memory, vm100Computation, vm100Bandwidth, n); 
        System.out.println("Combination 10 : VM = 100 Processes = 100 (Set 2) .... Ends");
        
        // *********************************************
        System.out.println("Combination 11 : VM = 100 Processes = 200 .... Starts");
        m = 200; 
        n = 100; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process200memoryRequirement, process200timeForMemoryRequirement, process200computationRequirement, process200bandwidthRequirement, process200timeForBandwidthRequirement, m, vm100Memory, vm100Computation, vm100Bandwidth, n); 
        System.out.println("Combination 11 : VM = 100 Processes = 200 .... Ends");
        
        // *********************************************
        System.out.println("Combination 12 : VM = 100 Processes = 500 .... Starts");
        m = 500; 
        n = 100; 
        System.out.println("Total processes " + m);
        System.out.println("Total VMs " + n);
        firstFit(process500memoryRequirement, process500timeForMemoryRequirement, process500computationRequirement, process500bandwidthRequirement, process500timeForBandwidthRequirement, m, vm100Memory, vm100Computation, vm100Bandwidth, n); 
        System.out.println("Combination 12 : VM = 100 Processes = 500 .... Ends");
        
        }  
    }  