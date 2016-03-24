
package sd.clt.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sd.clt.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListAlbums_QNAME = new QName("http://srv.sd/", "listAlbums");
    private final static QName _FileNotFoundException_QNAME = new QName("http://srv.sd/", "FileNotFoundException");
    private final static QName _ListPicturesResponse_QNAME = new QName("http://srv.sd/", "listPicturesResponse");
    private final static QName _ListAlbumsResponse_QNAME = new QName("http://srv.sd/", "listAlbumsResponse");
    private final static QName _ListPictures_QNAME = new QName("http://srv.sd/", "listPictures");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sd.clt.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListPictures }
     * 
     */
    public ListPictures createListPictures() {
        return new ListPictures();
    }

    /**
     * Create an instance of {@link ListAlbumsResponse }
     * 
     */
    public ListAlbumsResponse createListAlbumsResponse() {
        return new ListAlbumsResponse();
    }

    /**
     * Create an instance of {@link ListPicturesResponse }
     * 
     */
    public ListPicturesResponse createListPicturesResponse() {
        return new ListPicturesResponse();
    }

    /**
     * Create an instance of {@link FileNotFoundException }
     * 
     */
    public FileNotFoundException createFileNotFoundException() {
        return new FileNotFoundException();
    }

    /**
     * Create an instance of {@link ListAlbums }
     * 
     */
    public ListAlbums createListAlbums() {
        return new ListAlbums();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAlbums }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "listAlbums")
    public JAXBElement<ListAlbums> createListAlbums(ListAlbums value) {
        return new JAXBElement<ListAlbums>(_ListAlbums_QNAME, ListAlbums.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "FileNotFoundException")
    public JAXBElement<FileNotFoundException> createFileNotFoundException(FileNotFoundException value) {
        return new JAXBElement<FileNotFoundException>(_FileNotFoundException_QNAME, FileNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListPicturesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "listPicturesResponse")
    public JAXBElement<ListPicturesResponse> createListPicturesResponse(ListPicturesResponse value) {
        return new JAXBElement<ListPicturesResponse>(_ListPicturesResponse_QNAME, ListPicturesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAlbumsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "listAlbumsResponse")
    public JAXBElement<ListAlbumsResponse> createListAlbumsResponse(ListAlbumsResponse value) {
        return new JAXBElement<ListAlbumsResponse>(_ListAlbumsResponse_QNAME, ListAlbumsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListPictures }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://srv.sd/", name = "listPictures")
    public JAXBElement<ListPictures> createListPictures(ListPictures value) {
        return new JAXBElement<ListPictures>(_ListPictures_QNAME, ListPictures.class, null, value);
    }

}
