package main;

import com.google.auth.oauth2.GoogleCredentials;
// [START fs_include_dependencies]
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FireBaseDB {

	private Firestore db;
	
	public Firestore getDb() throws Exception {
		
		if(db==null) {
				FileInputStream serviceAccount;
				serviceAccount = new FileInputStream("./jogos-usados-firebase-adminsdk-syxx2-fef0113d56.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//					.setDatabaseUrl("https://jogos-usados.firebaseio.com")
//					.setProjectId("jogos-usados")
					.build();
			
			FirebaseApp.initializeApp(options);
			db = FirestoreClient.getFirestore();
		}
		
		return db;

	}
}
