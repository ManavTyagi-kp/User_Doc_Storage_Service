<!-- /TITLE -->
# User_Doc_Storage_Service
<!-- /TITLE -->

This REST API enables users to save and retreive files from an Amazon S3 bucket. 

# Saving File
  
  The first screenshot shows how users can upload a Multipart File using PostMan.
    ![image](https://user-images.githubusercontent.com/83765713/222922664-b876b570-dd2f-46a4-bd77-efb0cbe88bf7.png)

  The second image shows how this API protects users from adding multiple files of the same name in one folder.
  Multiple files of same name can cause problem in searching and downloading files.
    ![image](https://user-images.githubusercontent.com/83765713/222922682-6cd357cb-e5b0-40a3-90b3-5436d716ba81.png)
    

# All Docs
  Third image shows the "/alldocs" request, this request retreives all the folders and files present in the bucket.
    ![image](https://user-images.githubusercontent.com/83765713/222922902-80977f36-ae08-4c03-a0be-fad3ad4667bd.png)
    
     
# Searching and Downloading file
 
  Fourth image shows a search request
    ![image](https://user-images.githubusercontent.com/83765713/222922960-09585190-76be-4f0e-b8a3-8f385af9da5d.png)
  The fifth image shows how the API searches inside a user folder to check for files.
  The arrays in the "Run" dialog box represents the results fetched by searching in the user folder.
    ![image](https://user-images.githubusercontent.com/83765713/222923428-118f0dfe-2659-4c28-b590-c6713c0fdad0.png)

  To download a file, an request similar to the one above can be given.
  This request takes "userName" and "fileName" as Path Variables instead of the Requesting Body.
  This enables us to run the request on a browser, and the download of the file begins if it is found.
    ![image](https://user-images.githubusercontent.com/83765713/222923041-0ac3e3f4-5ac2-4bde-a6ff-a322936cf291.png)


 # File Not Found
 
 ![image](https://user-images.githubusercontent.com/83765713/222923707-42ffa960-a117-475d-82a5-a8eed285f734.png)
 ![image](https://user-images.githubusercontent.com/83765713/222923710-6d928f7d-af2c-450f-84fd-377b3568e6c3.png)

