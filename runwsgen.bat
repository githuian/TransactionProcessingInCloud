set class=edu.udel.tpic.server.soap.EntityAPI
set clpth=C:\Users\neosky\git\TransactionProcessingInCloud\war\WEB-INF\classes
set resourcedir=C:\Users\neosky\git\TransactionProcessingInCloud\war
set outsourcedir=C:\Users\neosky\git\TransactionProcessingInCloud\src
set outdir=C:\Users\neosky\git\TransactionProcessingInCloud\war\WEB-INF\classes
wsgen -cp "%clpth%" -wsdl -keep -r "%resourcedir%" -d "%outdir%" -s "%outsourcedir%" %class%	