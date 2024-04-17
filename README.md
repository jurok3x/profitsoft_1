# Article App

This application for parsing JSON with array of *Article* entity and generating XML report of articles count based on provided entity field.

## Usage

### Running the Application

#### Using Maven build

1. Clone the repository:
   ```bash
   git clone https://github.com/jurok3x/profitsoft_1.git

2. Navigate to the project directory:
    ```bash
   cd profitsoft_1
3. Run *maven package*
4. Navigate to the target directory:
    ```bash
   cd target
5. Run:
    ```bash
   java -jar articles-1.0-SNAPSHOT-jar-with-dependencies.jar $path $parameter
   
where: 
1. $path - path to JSON directory;
2. $parameter - one of Article field;

## Entity: Article

- **`title`** (String)
- **`author`** (String)
- **`field`** (String)
- **`year`** (Integer)
- **`journal`** (String)

## Example Input JSON

```json
[
    {
        "title": "Lorem Ipsum",
        "author": "John Doe",
        "year": 2022,
        "journal": "Sample Journal",
        "field": "biology"
    },
    {
        "title": "Dolor Sit Amet",
        "author": "Jane Smith",
        "year": 2023,
        "journal": "Another Journal",
        "field": "physics"
    }
]
```
## Example Output XML
```xml
<statistics>
    <item>
      <value>2022</value>
      <count>1</count>
    </item>
    <item>
      <value>2023</value>
      <count>1</count>
    </item>
  </statistics>
```

## Performance
- Execution time 1 Thread : 1075.6291 milliseconds
- Execution time 2 Threads: 654.5704 milliseconds
- Execution time 4 Threads : 393.6351 milliseconds
- Execution time 8 Threads: 311.3369 milliseconds

#### Conditions
- CPU: І5-9300Н.
- Prepared 200 JSON files with 100 articles.