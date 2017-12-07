/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverjsh;

import java.io.*;
import java.net.*;


class NewConnectThread extends Thread {
	
	Socket s;
	int num;
	
	public NewConnectThread(int num, Socket s)
	{
		// копируем данные
		this.num = num;
		this.s = s;

		// и запускаем новый вычислительный поток (см. ф-ю run())
		setDaemon(true);
		setPriority(NORM_PRIORITY);
		start();
	}

	public void run()
	{
		try
		{	
			System.out.println("S <--packet! K");
						
			// из сокета клиента берём поток входящих данных
			InputStream is = s.getInputStream();
			// и оттуда же - поток данных от сервера к клиенту
			OutputStream os = s.getOutputStream();

			// буфер данных в 64 килобайта
			byte buf[] = new byte[64*1024];
			// читаем 64кб от клиента, результат - кол-во реально принятых данных
			int r = is.read(buf);
			// создаём строку, содержащую полученную от клиента информацию
			String data = new String(buf, 0, r);

			// добавляем данные об адресе сокета:
			data = ""+num+": "+data;

			// выводим данные: 
			os.write(data.getBytes());
			System.out.println(data);

			// завершаем соединение
			s.close();
					
		}
		catch(Exception e)
		{System.out.println("init error: "+e);} // вывод исключений
	}
	
}
