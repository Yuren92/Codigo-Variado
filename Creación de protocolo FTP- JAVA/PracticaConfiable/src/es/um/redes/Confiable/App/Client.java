package es.um.redes.Confiable.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.file.Files;

import es.um.redes.Confiable.App.Proto.AppData;
import es.um.redes.Confiable.App.Proto.ByteIO;
import es.um.redes.Confiable.Intercambio.ReliableConnectionImpl;

public class Client {

	ReliableConnectionImpl conexion;

	private void startConversation(String remoteHostName) {
		conexion = new ReliableConnectionImpl("Log del cliente");
		try {
			conexion.connect(InetAddress.getByName(remoteHostName));
		} catch (IOException e2) {
			System.err.println("host desconocido");
		}

		boolean flag = true;
		BufferedReader buffeR = new BufferedReader(new InputStreamReader(
				System.in));
		String linea = null;
		String[] comando;
		while (flag) {
			System.out.print("Introduce un comando: ");
			// Leemos la linea que le mandamos.
			try {
				linea = buffeR.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Separamos los campos(la variable comando es un array de string)
			comando = linea.split(" ");

			// comprobamos si el comando tiene o no dos palabras
			// Si es asi comprueba si es un PUT o un GET o se ha equivocado de
			// comando.
			if (comando.length == 2) {
				if (comando[0].equals("put")) {
					File f1 = new File(comando[1]);
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(f1);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					long filelenght = f1.length();
					byte data[] = new byte[(int) filelenght];
					try {
						fis.read(data);
					} catch (IOException e) {
						e.printStackTrace();
					}

					AppData datos = new AppData(AppData.OP_PUT, comando[1],
							data);
					try {
						conexion.write(ByteIO.toByteArray(datos));
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					AppData retData;

					try {
						data = Files.readAllBytes(f1.toPath());
						retData = new AppData(AppData.OP_FILE, comando[1], data);
					} catch (IOException e) {
						retData = new AppData(AppData.OP_ERROR, comando[1],
								"Error recuperando el fichero.".getBytes());
					}
					AppData lectura = null;
					try {
						lectura = ByteIO.fromConnection(conexion);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					// caso ok
					if (lectura.getOPCode() == 3) {
						System.out
								.println("La operaci??n put se ha realizado de forma correcta");
					}
					// caso error
					if (lectura.getOPCode() == 4) {
						System.out
								.println("Error al realizar la operaci??n put");
					}

					try {
						conexion.write(ByteIO.toByteArray(retData));
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else if (comando[0].equals("get")) {
					AppData retData = null;

					// File name

					AppData datos = new AppData(AppData.OP_GET, comando[1],
							null);

					File origfilename = new File(datos.getFilename());
					String filename = "carpetaCliente" + File.separator
							+ origfilename.getName();
					File retfile = new File(filename);
					// Crear los directorios si no existen
					retfile.getParentFile().mkdirs();
					// Crear los directorios si no existen
					try {
						conexion.write(ByteIO.toByteArray(datos));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					AppData lectura = null;
					try {
						lectura = ByteIO.fromConnection(conexion);
					} catch (IOException e2) {
						e2.printStackTrace();
					}

					try {
						// Write file
						Files.write(retfile.toPath(), lectura.getData());
						retData = new AppData(AppData.OP_OK, filename, null);
					} catch (IOException e) {
						retData = new AppData(AppData.OP_ERROR, filename,
								"No se puede crear el fichero.".getBytes());
					}

					try {
						conexion.write(ByteIO.toByteArray(retData));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else
					System.out
							.println("ERROR:El comando introducido no es v??lido.");
				// comprobamos si es un exit.
			} else if (linea.equals("exit")) {
				AppData salida = new AppData(AppData.OP_END, null, null);
				try {
					conexion.write(ByteIO.toByteArray(salida));
					flag = false;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				 // ponemos la ejecucion como que ha terminado.
			} else {
				System.out
						.println("ERROR:El comando introducido no es v??lido.");
			}
		}
		// cerramos la conexion
		
		try {
			conexion.close();
			conexion.cierraFichero();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java TCPClient <hostname>");
			return;
		}

		Client client = new Client();
		String IP ;
		System.out.println("Introduce la direcci??n IP del servidor:" );
		//falta leer la entrada
		 client.startConversation(IP);
		System.exit(0);
	}
}
